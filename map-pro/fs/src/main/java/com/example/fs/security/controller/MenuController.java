package com.example.fs.security.controller;// package com.aisino.htlz.ai.platform.security.controller;


import cn.hutool.core.lang.tree.Tree;
import com.example.fs.common.JsonResponse;

import com.example.fs.security.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author Adss
 * @date 2024/6/24 下午1:25
 */
@RestController
@RequestMapping("/api/v1/menu")
@Validated
@RequiredArgsConstructor
@Tag(name = "菜单管理")
public class MenuController {

    private final MenuService menuService;

    @Operation(summary = "查询")
    @GetMapping("/select")
    public JsonResponse<List<Tree<String>>> select() {
        List<Tree<String>> menuNode = menuService.selectMenu();
        return new JsonResponse<List<Tree<String>>>().data(menuNode).success();
    }
}
