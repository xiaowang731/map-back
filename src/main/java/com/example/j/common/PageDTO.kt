package com.example.j.common

import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import java.io.Serializable


@Schema(description = "分页")
data class PageDTO(
    @Schema(description = "当前页码 >= 1", required = true)
    @Min(value = 1)
    @NotNull
    var pageNum: Long = 1,

    @Schema(description = "每页记录", required = true)
    @Min(value = 1, message = "pageSize 位于 [1-9999]")
    @Max(value = 9999, message = "pageSize 位于 [1-9999]")
    @NotNull
    var pageSize: Long = 100,
) : Serializable {
    companion object {
        @JvmStatic
        fun empty(): PageDTO = PageDTO(pageNum = 1, pageSize = 0)

        @JvmStatic
        fun of(pageNum: Long?, pageSize: Long?, defaultSize: Long = 100): PageDTO {
            var pageNum = pageNum
            var pageSize = pageSize
            if (pageNum == null || pageNum < 1) {
                pageNum = 1
            }
            if (pageSize == null) {
                pageSize = defaultSize
            }
            if (pageSize > 100) {
                pageSize = 100
            }
            val dto = PageDTO()
            dto.pageNum = pageNum
            dto.pageSize = pageSize
            return dto
        }

        @JvmStatic
        fun of(pageNum: Long?, pageSize: Long?): PageDTO {
            var pageNum = pageNum
            var pageSize = pageSize
            if (pageNum == null || pageNum < 1) {
                pageNum = 1
            }
            if (pageSize == null) {
                pageSize = 100
            }
            if (pageSize > 100) {
                pageSize = 100
            }
            val dto = PageDTO()
            dto.pageNum = pageNum
            dto.pageSize = pageSize
            return dto
        }

        @JvmStatic
        fun ofWithMax(pageNum: Long?, pageSize: Long?, maxSize: Long = 100): PageDTO {
            var pageNum = pageNum
            var pageSize = pageSize
            if (pageNum == null || pageNum < 1) {
                pageNum = 1
            }
            if (pageSize == null) {
                pageSize = 100
            }
            if (pageSize > maxSize) {
                pageSize = maxSize
            }
            val dto = PageDTO()
            dto.pageNum = pageNum
            dto.pageSize = pageSize
            return dto
        }
    }

    fun <T> toMybatisPage() = Page<T>(this.pageNum, this.pageSize)
}
