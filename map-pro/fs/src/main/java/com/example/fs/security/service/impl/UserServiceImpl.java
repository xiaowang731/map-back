package com.example.fs.security.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.fs.captch.CaptchaServiceImpl;
import com.example.fs.common.JsonResponse;
import com.example.fs.common.PageDTO;
import com.example.fs.common.PageVO;
import com.example.fs.common.exception.ParameterException;
import com.example.fs.common.util.Assert;
import com.example.fs.common.util.JwtUtil;
import com.example.fs.security.mapper.RoleMapper;
import com.example.fs.security.mapper.UserMapper;
import com.example.fs.security.mapper.UserRoleMapper;
import com.example.fs.security.pojo.*;
import com.example.fs.security.service.UserRoleService;
import com.example.fs.security.service.UserService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Adss
 * @date 2024/6/20 下午3:38
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    private final MenuServiceImpl menuService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleMapper userRoleMapper;
    private final UserRoleService userRoleService;
    private final RoleMapper roleMapper;
    private final CaptchaServiceImpl captchaService;
    @Resource
    private AuthenticationManager authenticationManager;

    @Override
    public UserVO login(LoginDTO loginDto) {
        Assert.isTrue(captchaService.checkCode(loginDto.getCode()), "验证码错误");

        LoginUser loginUser = authentication(loginDto);

        User user = loginUser.getUser();
        String id = user.getId();

        Assert.isFalse(Objects.requireNonNull(user.getStatus()),
                "账号已被停用，请联系管理员");
        UserVO userVO = BeanUtil.toBean(user, UserVO.class);

        userVO.setToken(JwtUtil.createJwt(id));

        UserVO routerPerm = menuService.getRouterPerm(id);
        userVO.setRouterList(routerPerm.getRouterList());
        userVO.setPermissionsList(routerPerm.getPermissionsList());

        baseMapper.updateLoginTime(id, LocalDateTime.now());

        redisTemplate.opsForValue().set("login:" + id, loginUser, 8, TimeUnit.HOURS);

        return userVO;
    }

    @Override
    public UserVO selectCurrentUser() {
        User user = currentUser(true);
        Assert.notNull(user, "用户未登录");
        return BeanUtil.toBean(user, UserVO.class);
    }

    @Override
    public void updatePerson(UpdatePersonDto dto) {
        Boolean isAdmin = currentUserIsAdmin();
        if (isAdmin) {
            Assert.isFalse(validationUsername(BeanUtil.toBean(dto, User.class)), "账号已存在");
            Assert.notNull(dto.getStatus(), "状态不能为空");
        }

        String id = dto.getId();
        String nickname = dto.getNickname();
        String avatar = dto.getAvatar();
        String email = dto.getEmail();
        String phone = dto.getPhone();
        Boolean status = dto.getStatus();
        String firstPassword = dto.getFirstPassword();
        String secondPassword = dto.getSecondPassword();

        Assert.isFalse("1".equals(id), "admin不允许被修改");
        Assert.isTrue(StrUtil.equals(firstPassword, secondPassword), "俩次密码输入不正确");

        String encode = null;
        if (StrUtil.isNotBlank(firstPassword)) {
            encode = passwordEncoder.encode(firstPassword);
        }

        update(
                Wrappers.<User>lambdaUpdate()
                        .set(StrUtil.isNotBlank(nickname), User::getNickname, nickname)
                        .set(StrUtil.isNotBlank(avatar), User::getAvatar, avatar)
                        .set(email != null, User::getEmail, email)
                        .set(phone != null, User::getPhone, phone)
                        .set(encode != null, User::getPassword, encode)
                        .set(isAdmin, User::getStatus, status)
                        .eq(User::getId, id)
        );

        if (currentUserIsAdmin()) {
            setUserRoles(dto);
            redisTemplate.delete("login:" + id);
        }
    }

    /**
     * true || null :缓存
     * false : 数据库
     *
     * @param isCache
     * @return
     */
    @Override
    public User currentUser(Boolean isCache) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof LoginUser loginUser)) {
            return null;
        }

        User user = loginUser.getUser();

        return Boolean.TRUE.equals(isCache) ? user : getById(user.getId());
    }

    @Override
    public Boolean currentUserIsAdmin() {
        User user = currentUser(true);
        if (user == null) {
            return false;
        }
        String id = user.getId();
        List<UserRole> userRoles = userRoleMapper.selectList(
                Wrappers.<UserRole>lambdaQuery()
                        .eq(UserRole::getUserId, id)
        );
        List<String> list = userRoles.stream()
                .map(UserRole::getRoleId)
                .distinct()
                .toList();
        return list.contains("1");
    }


    @Override
    public void loginOut() {
        String id = Objects.requireNonNull(currentUser(null)).getId();
        redisTemplate.delete("login:" + id);
    }

    @Override
    @Transactional
    public void add(AddUserDTO dto) {
        Assert.isFalse(validationUsername(BeanUtil.toBean(dto, User.class)), "账号已存在");
        if (StrUtil.isBlank(dto.getPassword())) {
            dto.setPassword("123456");
        }
        User user = BeanUtil.toBean(dto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(false);
        save(user);

        userRoleService.save(new UserRole(user.getId(), "1805497020416331778"));
    }

    @Override
    @Transactional
    public void delete(DeleteUserDTO dto) {
        String id = dto.getId();
        Assert.isFalse(id == null || "1".equals(id), "admin不允许被删除");

        update(
                Wrappers.lambdaUpdate(User.class)
                        .eq(User::getId, id)
                        .eq(User::getRemoved, false)
                        .set(User::getRemoved, true)
        );

        // 删除关联角色信息
        userRoleMapper.delete(
                Wrappers.lambdaQuery(UserRole.class)
                        .eq(UserRole::getUserId, id)
        );

        redisTemplate.delete("login:" + id);
    }

    @Override
    @Transactional
    public void update(UpdateUserDTO dto) {
        Assert.isFalse(validationUsername(BeanUtil.toBean(dto, User.class)), "账号已存在");
        String id = dto.getId();
        String nickname = dto.getNickname();
        Boolean status = dto.getStatus();
        String email = dto.getEmail();
        String phone = dto.getPhone();

        Assert.isFalse("1".equals(id), "admin不允许被修改");

        update(
                Wrappers.lambdaUpdate(User.class)
                        .eq(User::getId, id)
                        .eq(User::getRemoved, false)
                        .set(User::getStatus, status)
                        .set(nickname != null, User::getNickname, nickname)
                        .set(email != null, User::getEmail, email)
                        .set(phone != null, User::getPhone, phone)

        );


        redisTemplate.delete("login:" + id);
    }

    @Override
    public PageVO<UserVO> select(UserPageDTO dto) {
        Page<User> page = getUserPage(dto);

        List<User> user = page.getRecords();

        List<String> uIds = Optional.ofNullable(user).orElse(Collections.emptyList())
                .stream()
                .map(User::getId)
                .distinct()
                .toList();

        if (uIds.isEmpty()) {
            return PageVO.of(new ArrayList<>(0), page.getTotal());
        }

        List<UserRole> urs = userRoleMapper.selectList(
                Wrappers.lambdaQuery(UserRole.class)
                        .in(UserRole::getUserId, uIds)
        );

        List<String> rIds = Optional.ofNullable(urs).orElse(Collections.emptyList())
                .stream()
                .map(UserRole::getRoleId)
                .distinct()
                .toList();
        if (rIds.isEmpty()) {
            return PageVO.of(
                    user.stream()
                            .map(u -> BeanUtil.toBean(u, UserVO.class))
                            .collect(Collectors.toList()),
                    page.getTotal());
        }

        // 按角色ID对用户ID进行分组
        Map<String, List<String>> rId2uIds = urs.stream()
                .collect(Collectors.groupingBy(UserRole::getRoleId,
                        Collectors.mapping(UserRole::getUserId, Collectors.toList())));
        // 创建用户ID到关联角色的映射
        Map<String, List<Role>> uId2rs = new HashMap<>();
        roleMapper.selectBatchIds(rIds).forEach(r -> {
                    List<String> uId = rId2uIds.get(r.getId());
                    if (uId != null) {
                        uId.forEach(rId -> {
                            List<Role> role = uId2rs.computeIfAbsent(rId, ignored -> new ArrayList<>());
                            role.add(r);
                        });
                    }
                }
        );

        // 将 User 实体映射为带有关联角色的 UserVOs
        List<UserVO> userVos = user.stream()
                .map(u -> {
                    UserVO userVO = BeanUtil.toBean(u, UserVO.class);
                    userVO.setRole(uId2rs.getOrDefault(u.getId(), new ArrayList<>())
                            .stream()
                            .map(role -> BeanUtil.toBean(role, RoleVO.class))
                            .collect(Collectors.toList()));
                    return userVO;
                })
                .collect(Collectors.toList());
        return PageVO.of(userVos, page.getTotal());

    }

    private Page<User> getUserPage(UserPageDTO dto) {
        String nickname = dto.getNickname();
        String phone = dto.getPhone();

        PageDTO pg = PageDTO.of(dto.getPageNum(), dto.getPageSize());


        return page(pg.toMybatisPage(),
                Wrappers.lambdaQuery(User.class)
                        .like(StrUtil.isNotBlank(nickname), User::getNickname, nickname)
                        .like(StrUtil.isNotBlank(phone), User::getPhone, phone)
                        .eq(User::getRemoved, false)
                        .eq(!currentUserIsAdmin(), User::getId, Objects.requireNonNull(currentUser(true)).getId())
                        .orderByAsc(User::getNickname)
        );
    }


    /**
     * 设置用户角色关联关系，根据更新用户信息中的角色列表更新用户角色信息。
     *
     * @param dto 更新用户信息的数据传输对象
     */
    private void setUserRoles(UpdatePersonDto dto) {
        String id = dto.getId();

        userRoleMapper.delete(
                Wrappers.lambdaQuery(UserRole.class)
                        .eq(UserRole::getUserId, id)
        );

        List<UserRole> userRoles = Optional.ofNullable(dto.getRole())
                .map(ids -> ids.stream()
                        .map(roleId -> new UserRole(id, roleId))
                        .collect(Collectors.toList())
                )
                .orElse(Collections.emptyList());
        if (!userRoles.isEmpty()) {
            userRoleService.saveBatch(userRoles);
        }
    }


    /**
     * 验证用户名的唯一性。
     *
     * @param user 待验证的用户对象
     * @return 如果用户名唯一，则返回true；否则返回false
     */
    private boolean validationUsername(User user) {
        // 如果one为空表示添加的昵称唯一
        // 如果one不为空，user.id为空表示添加
        // 如果one不为空，user.id不为表示修改，比较id值
        var one = getUser(user);
        if (one == null) {
            return false;
        } else if (user.getId() == null) {
            return true;
        } else {
            return !Objects.equals(one.getId(), user.getId());
        }
    }

    private User getUser(User user) {
        try {
            return baseMapper.selectOne(
                    Wrappers.lambdaQuery(User.class)
                            .eq(User::getUsername, user.getUsername())
                            .eq(User::getRemoved, false)
            );
        } catch (Exception e) {
            throw new ParameterException(JsonResponse.FAIL, "昵称已存在");
        }
    }

    private LoginUser authentication(LoginDTO loginDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()
                );
        Authentication authenticate;
        try {
            authenticate = authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException e) {
            throw new ParameterException("用户名或密码错误");
        }
        return (LoginUser) authenticate.getPrincipal();
    }
}