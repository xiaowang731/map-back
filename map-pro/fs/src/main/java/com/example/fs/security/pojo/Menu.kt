package com.example.fs.security.pojo


import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName

/**
 * @author Adss
 * @date 2024/6/20 下午3:38
 */

@TableName("menu")
data class Menu(
    var id: String? = null,
    var name: String? = null,
    var path: String? = null,
    var perms: String? = null,
    var icon: String? = null,
    @TableField(fill = FieldFill.INSERT)
    var removed: Boolean? = null,
    var type: Int? = null,
    @TableField("`order`")
    var order: Int? = null,
    var parent: String? = null,
)

data class PermissionList(
    var path: String? = null,
    var perms: List<String>? = null,
)

data class RoleMenu(
    var menuId: String,
    var roleId: String
)
