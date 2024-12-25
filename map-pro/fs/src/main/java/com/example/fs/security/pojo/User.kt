package com.example.fs.security.pojo

import cn.hutool.core.lang.tree.Tree
import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

/**
 * @author Adss
 * @date 2024/6/20 下午3:38
 */
@TableName("user")
data class User(
    var id: String? = null,
    var username: String? = null,
    var password: String? = null,
    var nickname: String? = null,
    var status: Boolean? = null,
    var email: String? = null,
    var avatar: String? = null,
    var phone: String? = null,
    @TableField(fill = FieldFill.INSERT)
    var createdTime: LocalDateTime? = null,
    var loginTime: LocalDateTime? = null,
    @TableField(fill = FieldFill.INSERT)
    var removed: Boolean? = null,
)

data class LoginDTO(
    @Schema(title = "名称")
    @field:NotBlank(message = "账号不能为空")
    var username: String,
    @Schema(title = "密码")
    @field:NotBlank(message = "密码不能为空")
    var password: String,
    @field:NotBlank(message = "验证码不能为空")
    var code: String,
)

data class AddUserDTO(
    @field:NotBlank(message = "账号不能为空")
    var username: String? = null,
    @field:NotBlank(message = "昵称不能为空")
    var nickname: String? = null,
    var password: String? = null,
)

data class DeleteUserDTO(
    @field:NotBlank(message = "用户id不能为空")
    var id: String? = null,
)

data class UpdatePersonDto(
    @field:NotNull(message = "用户id不能为空")
    var id: String,
    var email: String? = null,
    var nickname: String? = null,
    var phone: String? = null,
    @Schema(title = "第一次密码")
    var firstPassword: String? = null,
    @Schema(title = "第二次密码")
    var secondPassword: String? = null,
    var avatar: String? = null,

    var status: Boolean? = null,
    var role: List<String>? = null,
)

data class UpdateUserDTO(
    @field:NotNull(message = "用户id不能为空")
    var id: String,
    var email: String? = null,
    var nickname: String? = null,
    @field:NotNull(message = "用户状态不能为空")
    var status: Boolean? = null,
    var phone: String? = null,
    var role: List<String>? = null,
)

data class UserPageDTO(
    var nickname: String? = null,
    var phone: String? = null,
    var pageNum: Long? = null,
    var pageSize: Long? = null
)

data class UserVO(
    @Schema(title = "用户id")
    var id: String? = null,
    @Schema(title = "账号")
    var username: String? = null,
    @Schema(title = "token")
    var token: String? = null,
    @Schema(title = "头像id")
    var avatar: String? =null,
    @Schema(title = "昵称")
    var nickname: String? = null,
    @Schema(title = "状态")
    var status: Boolean? = null,
    @Schema(title = "邮箱")
    var email: String? = null,
    @Schema(title = "电话")
    var phone: String? = null,
    @Schema(title = "创建时间")
    var createdTime: LocalDateTime? = null,
    @Schema(title = "上次登录时间")
    var loginTime: LocalDateTime? = null,
    @Schema(title = "角色集")
    var role: List<RoleVO>? = listOfNotNull(),
    @Schema(title = "路由菜单")
    var routerList: List<Tree<String>>? = listOfNotNull(),
    @Schema(title = "权限")
    var permissionsList: List<PermissionList>? = listOfNotNull(),
)
