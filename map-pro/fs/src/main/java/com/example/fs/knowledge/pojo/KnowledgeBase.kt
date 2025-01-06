package com.example.fs.knowledge.pojo

import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import java.time.LocalDateTime

/**
 * @author Adss
 * @data 2025/1/6 14:06
 */
@TableName(" knowledge_base")
data class knowledgeBase  (
    var id: String? = null,
    var title: String? = null,
    var icon: String? = null,
    var userId: String? = null,
    @TableField(fill = FieldFill.INSERT)
    var createdTime: LocalDateTime? = null,
    var updateTime: LocalDateTime? = null,
    @TableField(fill = FieldFill.INSERT)
    var removed: Boolean? = null,
)