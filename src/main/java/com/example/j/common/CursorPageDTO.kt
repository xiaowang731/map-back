package com.example.j.common

import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable

@Schema(description = "游标分页")
data class CursorPageDTO(
    @Schema(description = "上一条记录的 id", required = true)
    var last: String = "",

    @Schema(description = "记录数", required = true)
    var size: Long = 50,
) : Serializable
