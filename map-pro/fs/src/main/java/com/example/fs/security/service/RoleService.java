package com.example.fs.security.service;


import com.example.fs.common.PageVO;
import com.example.fs.security.pojo.*;


/**
 * @author Adss
 * @date 2024/6/20 下午3:38
 */
public interface RoleService {
    void add(AddRoleDTO dto);

    void remove(DeleteRoleDTO dto);

    void update(UpdateRoleDTO dto);

    PageVO<RoleVO> selectByPage(RolePageDTO dto);

    void bindRoleMenu(BindRoleMenuDTO dto);
}
