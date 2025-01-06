package com.example.fs.knowledge.pojo

import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

/**
 * @author Adss
 * @data 2025/1/6 14:02
 */
@TableName(" knowledge_text")
data class KnowledgeText (
    var id: String? = null,
    var title: String? = null,
    var to: String? = null,
    var knowledgeBaseId: String? = null,
    @TableField(fill = FieldFill.INSERT)
    var createdTime: LocalDateTime? = null,
    var updateTime: LocalDateTime? = null,
    @TableField(fill = FieldFill.INSERT)
    var removed: Boolean? = null,
)

data class AddKnowledgeTextDTO(
    @Schema(title = "标题")
    @field:NotBlank(message = "标题不能为空")
    var title: String? = null,
    var to: String? = null,
    @Schema(title = "父目录ID")
    @field:NotBlank(message = "父目录ID不能为空")
    var knowledgeBaseId: String? = null,
)

data class UpdateKnowledgeTextDTO(
    @field:NotBlank(message = "ID不能为空")
    var id: String? = null,
    @Schema(title = "标题")
    @field:NotBlank(message = "标题不能为空")
    var title: String? = null,
    var to: String? = null,
    @Schema(title = "父目录ID")
    @field:NotBlank(message = "父目录ID不能为空")
    var knowledgeBaseId: String? = null,
)

data class DeleteKnowledgeTextDTO(
    @field:NotBlank(message = "ID不能为空")
    var id: String? = null,
)
