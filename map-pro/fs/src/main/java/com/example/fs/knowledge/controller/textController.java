package com.example.fs.knowledge.controller;

import com.example.fs.common.JsonResponse;
import com.example.fs.knowledge.pojo.AddKnowledgeTextDTO;
import com.example.fs.knowledge.pojo.DeleteKnowledgeTextDTO;
import com.example.fs.knowledge.pojo.UpdateKnowledgeTextDTO;
import com.example.fs.knowledge.service.KnowledgeTextService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Adss
 * @data 2025/1/6 15:38
 */
@RestController
@RequestMapping("/api/v1/knowledgeText")
@Validated
@RequiredArgsConstructor
@Tag(name = "知识库文章管理")
public class textController {
    private KnowledgeTextService knowledgeTextService;

    @Operation(summary = "添加文章")
    @PostMapping("/role")
    public JsonResponse<Object> add(@Validated @RequestBody AddKnowledgeTextDTO dto) {
        knowledgeTextService.add(dto);
        return new JsonResponse<>().success();
    }

    @Operation(summary = "删除文章")
    @DeleteMapping("/role")
    public JsonResponse<Object> deleteUserId(@Validated @RequestBody DeleteKnowledgeTextDTO dto) {
        knowledgeTextService.delete(dto);
        return new JsonResponse<>().success();
    }


    @Operation(summary = "更新文章")
    @PutMapping("/role")
    public JsonResponse<Object> updateById(@Validated @RequestBody UpdateKnowledgeTextDTO dto) {
        knowledgeTextService.update(dto);
        return new JsonResponse<>().success();
    }
}
