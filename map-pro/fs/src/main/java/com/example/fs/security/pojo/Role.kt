package com.example.fs.security.pojo

import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

/**
 * @author Adss
 * @date 2024/6/20 下午3:38
 */

@TableName("role")
data class Role(
    var id: String? = null,
    var name: String? = null,
    @TableField(fill = FieldFill.INSERT)
    var removed: Boolean? = null,
    @TableField(fill = FieldFill.INSERT)
    var createdTime: LocalDateTime? = null,
)

data class AddRoleDTO(
    @Schema(title = "角色名字")
    @field:NotBlank(message = "角色id不能为空")
    var name: String
)

data class DeleteRoleDTO(
    @Schema(title = "角色id")
    @field:NotBlank(message = "角色id不能为空")
    var id: String
)

data class UpdateRoleDTO(
    @Schema(title = "角色id")
    @field:NotBlank(message = "角色id不能为空")
    var id: String,
    @Schema(title = "角色名字")
    @field:NotBlank(message = "角色名字不能为空")
    var name: String? = null,
)

data class RolePageDTO(
    var name: String? = null,
    var pageNum: Long? = null,
    var pageSize: Long? = null,
)

data class BindRoleMenuDTO(
    @field:NotBlank(message = "角色id不能为空")
    var id: String,
    var checkMenu: List<List<String>>? = null,
)

data class RoleVO(
    var id: String? = null,
    var name: String? = null,
    var createdTime: LocalDateTime? = null,
    var checkMenu: List<String>? = null,
)

data class UserRole(
    var userId: String,
    var roleId: String,
)


