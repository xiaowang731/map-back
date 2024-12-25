package com.example.fs.security.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.fs.common.PageDTO;
import com.example.fs.common.PageVO;
import com.example.fs.common.util.Assert;
import com.example.fs.security.mapper.RoleMapper;
import com.example.fs.security.mapper.RoleMenuMapper;
import com.example.fs.security.pojo.*;
import com.example.fs.security.service.RoleMenuService;
import com.example.fs.security.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Adss
 * @date 2024/6/20 下午3:38
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {


    private final RoleMenuService roleMenuService;
    private final RoleMenuMapper roleMenuMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void add(AddRoleDTO dto) {
        Role role = BeanUtil.toBean(dto, Role.class);
        save(role);
    }

    @Override
    @Transactional
    public void remove(DeleteRoleDTO dto) {
        String id = dto.getId();
        Assert.isFalse("1".equals(id), "超级管理员角色不允许被删除");
        update(
                Wrappers.lambdaUpdate(Role.class)
                        .eq(Role::getRemoved, false)
                        .eq(Role::getId, dto.getId())
                        .set(Role::getRemoved, true)
        );

        roleMenuMapper.delete(
                Wrappers.lambdaQuery(RoleMenu.class)
                        .eq(RoleMenu::getRoleId, id)
        );

        redisTemplate.delete("login:*");
    }

    @Override
    public void update(UpdateRoleDTO dto) {
        String id = dto.getId();
        Assert.isFalse("1".equals(id), "超级管理员角色不允许被修改");

        update(
                Wrappers.lambdaUpdate(Role.class)
                        .eq(Role::getRemoved, false)
                        .eq(Role::getId, id)
                        .set(Role::getName, dto.getName())
        );

        redisTemplate.delete("login:*");
    }

    @Override
    @Transactional
    public void bindRoleMenu(BindRoleMenuDTO dto) {
        String id = dto.getId();
        Assert.isFalse("1".equals(id), "超级管理员的菜单权限不允许修改");

        roleMenuMapper.delete(
                Wrappers.lambdaQuery(RoleMenu.class)
                        .eq(RoleMenu::getRoleId, id)
        );

        List<List<String>> menuIdsDto = dto.getCheckMenu();
        if (CollectionUtil.isEmpty(menuIdsDto)) {
            return;
        }

        List<String> menusIds = menuIdsDto.stream()
                .flatMap(List::stream)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        List<RoleMenu> roleMenus = Optional.of(menusIds)
                .map(ids -> ids.stream()
                        .map(m -> new RoleMenu(m, id))
                        .collect(Collectors.toList())
                )
                .orElse(Collections.emptyList());
        if (!roleMenus.isEmpty()) {
            roleMenuService.saveBatch(roleMenus);
        }

        redisTemplate.delete("login:*");
    }

    @Override
    public PageVO<RoleVO> selectByPage(RolePageDTO dto) {

        Page<Role> page = getRolePage(dto);

        List<Role> roles = page.getRecords();

        List<String> rIds = Optional.ofNullable(roles).orElse(Collections.emptyList())
                .stream()
                .map(Role::getId)
                .distinct()
                .toList();

        if (rIds.isEmpty()) {
            return PageVO.of(new ArrayList<>(0), page.getTotal());
        }

        List<RoleMenu> roleMenus = roleMenuMapper.selectList(
                Wrappers.lambdaQuery(RoleMenu.class)
                        .in(RoleMenu::getRoleId, rIds)
        );
        if (roleMenus.isEmpty()) {
            return PageVO.of(roles.stream()
                    .map(role -> BeanUtil.toBean(role, RoleVO.class))
                    .collect(Collectors.toList()), page.getTotal());
        }

        Map<String, List<String>> roleId2MenuIds = roleMenus.stream()
                .collect(Collectors.groupingBy(RoleMenu::getRoleId,
                        Collectors.mapping(RoleMenu::getMenuId, Collectors.toList())));

        return PageVO.of(page.getRecords()
                .stream()
                .map(role -> {
                    RoleVO roleVO = BeanUtil.toBean(role, RoleVO.class);
                    roleVO.setCheckMenu(roleId2MenuIds.getOrDefault(role.getId(), new ArrayList<>()));
                    return roleVO;
                })
                .collect(Collectors.toList()), page.getTotal());
    }

    private Page<Role> getRolePage(RolePageDTO dto) {
        String name = dto.getName();

        PageDTO pg = PageDTO.of(dto.getPageNum(), dto.getPageSize());
        return page(pg.toMybatisPage(),
                Wrappers.lambdaQuery(Role.class)
                        .like(StrUtil.isNotBlank(name), Role::getName, name)
                        .eq(Role::getRemoved, false)
        );
    }

}
