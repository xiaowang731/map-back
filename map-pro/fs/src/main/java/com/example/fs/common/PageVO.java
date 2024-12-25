package com.example.fs.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Data
public class PageVO<E> implements Serializable {
    @Schema(description = "列表记录")
    private List<E> records;
    @Schema(description = "记录数量")
    private Long    total;

    static final PageVO EMPTY = new PageVO();

    static {
        EMPTY.setRecords(Collections.EMPTY_LIST);
        EMPTY.setTotal(0L);
    }

    /**
     * 分页结果集封装
     *
     * @param records 列表记录
     * @param total   总记录数
     */
    public static <E> PageVO<E> of(List<E> records, long total) {
        PageVO<E> vo = new PageVO<>();
        vo.setRecords(records);
        vo.setTotal(total);
        return vo;
    }

    /**
     * 返回一个空集合
     */
    public static <E> PageVO<E> ofEmpty() {
        return EMPTY;
    }
}
