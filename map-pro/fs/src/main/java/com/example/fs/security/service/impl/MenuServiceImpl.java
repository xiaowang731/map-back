package com.example.fs.security.service.impl;


import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.fs.security.mapper.MenuMapper;
import com.example.fs.security.pojo.Menu;
import com.example.fs.security.pojo.PermissionList;
import com.example.fs.security.pojo.UserVO;
import com.example.fs.security.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * @author Adss
 * @date 2024/6/20 下午3:38
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<Tree<String>> selectMenu() {
        return getTree(list());
    }

    /**
     * 获取用户的路由和权限信息
     *
     * @param userId 用户id
     * @return 包含路由和权限信息的 UserVO 对象
     */
    @Override
    public UserVO getRouterPerm(String userId) {
        List<Long> menuIds = baseMapper.selectByMenuId(userId);

        if (menuIds.isEmpty()) {
            return new UserVO();
        }

        List<Menu> menus = listByIds(menuIds);
        if (menus.isEmpty()) {
            return new UserVO();
        }
        Map<Boolean, List<Menu>> menuPartition = menus.stream()
                .collect(Collectors.partitioningBy(menu -> menu.getType() == 3));

        // 获取权限列表，路由列表
        List<Menu> perm = menuPartition.get(true);
        List<Menu> router = menuPartition.get(false);

        UserVO userVO = new UserVO();
        userVO.setRouterList(getTree(router));
        userVO.setPermissionsList(getPermissionsList(perm));

        return userVO;
    }


    /**
     * 根据路由菜单列表构建树，并返回
     *
     * @param menus 菜单列表
     * @return 根节点表示整个路由树的 MenuNode 对象
     */
    private List<Tree<String>> getTree(List<Menu> menus) {

        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setWeightKey("order");
        treeNodeConfig.setParentIdKey("parent");
        treeNodeConfig.setDeep(4);

        return TreeUtil.build(menus, "-1", treeNodeConfig,
                (menu, tree) -> {
                    tree.setId(menu.getId());
                    tree.setParentId(menu.getParent());
                    tree.setWeight(menu.getOrder());
                    tree.setName(menu.getName());
                    tree.putExtra("path", menu.getPath());
                    tree.putExtra("icon", menu.getIcon());
                    tree.putExtra("type", menu.getType());
                });
    }

    /**
     * 根据权限菜单列表构建权限列表
     *
     * @param perm 权限菜单列表
     * @return 权限列表，以路径为键，包含权限集合的 PermissionList 对象为值
     */
    private List<PermissionList> getPermissionsList(List<Menu> perm) {
        return perm.stream()
                .collect(Collectors.groupingBy(
                        // 将路径为null的菜单默认为"default"
                        menu -> Optional.ofNullable(menu.getPath()).orElse("default"),
                        Collectors.mapping(Menu::getPerms, Collectors.toList())))
                .entrySet().stream()
                .map(entry -> new PermissionList(
                        entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }


}
