package com.example.fs.security.controller;

import com.example.fs.common.JsonResponse;
import com.example.fs.common.PageVO;
import com.example.fs.security.pojo.*;
import com.example.fs.security.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Adss
 * @date 2024/6/24 下午1:30
 */
@RestController
@RequestMapping("/api/v1/role")
@Validated
@RequiredArgsConstructor
@Tag(name = "角色管理")
public class RoleController {

    private final RoleService roleService;

    @Operation(summary = "添加角色")
    @PostMapping("/role")
    public JsonResponse<Object> add(@Validated @RequestBody AddRoleDTO dto) {
        roleService.add(dto);
        return new JsonResponse<>().success();
    }

    @Operation(summary = "删除角色")
    @DeleteMapping("/role")
    public JsonResponse<Object> deleteUserId(@Validated @RequestBody DeleteRoleDTO dto) {
        roleService.remove(dto);
        return new JsonResponse<>().success();
    }


    @Operation(summary = "更新角色")
    @PutMapping("/role")
    public JsonResponse<Object> updateById(@Validated @RequestBody UpdateRoleDTO dto) {
        roleService.update(dto);
        return new JsonResponse<>().success();
    }

    @Operation(summary = "查询角色")
    @PostMapping("/role/page")
    public JsonResponse<PageVO<RoleVO>> selectByPage(
            @RequestBody RolePageDTO rolePageDto) {

        PageVO<RoleVO> pageVo = roleService.selectByPage(rolePageDto);
        return new JsonResponse<PageVO<RoleVO>>().data(pageVo).success();
    }

    @Operation(summary = "绑定")
    @PostMapping("/role/bind-menu")
    public JsonResponse<Object> bindRoleMenu(@Validated @RequestBody BindRoleMenuDTO bindRoleMenuDTO) {
        roleService.bindRoleMenu(bindRoleMenuDTO);
        return new JsonResponse<>().success();
    }

}
