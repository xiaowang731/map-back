package com.example.fs.security.controller;

import com.example.fs.common.JsonResponse;
import com.example.fs.common.PageVO;
import com.example.fs.security.pojo.*;
import com.example.fs.security.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Adss
 * @date 2024/6/20 下午3:38
 */
@RestController
@RequestMapping("/api/v1")
@Validated
@RequiredArgsConstructor
@Tag(name = "用户管理")
public class UserController {

    private final UserService userService;

    @Operation(summary = "登录")
    @PostMapping("/login")
    public JsonResponse<UserVO> login(@RequestBody @Validated LoginDTO loginDto) {
        UserVO login = userService.login(loginDto);
        return new JsonResponse<UserVO>().data(login).success();
    }

    @Operation(summary = "登出")
    @PostMapping("/loginOut")
    public JsonResponse<Object> loginOut() {
        userService.loginOut();
        return new JsonResponse<>().success();
    }

    @Operation(summary = "添加用户")
    @PostMapping("/user")
    @PreAuthorize("hasAuthority('user:add')")
    public JsonResponse<Object> add(@Validated @RequestBody AddUserDTO dto) {
        userService.add(dto);
        return new JsonResponse<>().success();
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/user")
    @PreAuthorize("hasAuthority('user:delete')")
    public JsonResponse<Object> delete(@Validated @RequestBody DeleteUserDTO dto) {
        userService.delete(dto);
        return new JsonResponse<>().success();
    }

    @Operation(summary = "更新用户")
    @PutMapping("/user")
    // @PreAuthorize("hasAuthority('user:update')")
    public JsonResponse<Object> update(@Validated @RequestBody UpdateUserDTO dto) {
        userService.update(dto);
        return new JsonResponse<>().success();
    }

    @Operation(summary = "查询用户")
    @PostMapping("/user/select")
    // @PreAuthorize("hasAuthority('user:select')")
    public JsonResponse<PageVO<UserVO>> select(@Validated @RequestBody UserPageDTO dto) {
        PageVO<UserVO> select = userService.select(dto);
        return new JsonResponse<PageVO<UserVO>>().data(select).success();
    }

    @Operation(summary = "个人信息修改")
    @PutMapping("/user/person")
    public JsonResponse<Object> updatePerson(@Validated @RequestBody UpdatePersonDto dto) {
        userService.updatePerson(dto);
        return new JsonResponse<>().success();
    }

    @Operation(summary = "获取当前用户")
    @GetMapping("/user/select/person")
    public JsonResponse<UserVO> getPerson() {
        UserVO userVO = userService.selectCurrentUser();
        return new JsonResponse<UserVO>().data(userVO).success();
    }


}
