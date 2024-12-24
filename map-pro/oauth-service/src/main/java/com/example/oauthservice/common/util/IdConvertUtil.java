package com.example.oauthservice.common.util;

import cn.hutool.core.collection.CollectionUtil;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class IdConvertUtil {
    public static String longConvert(List<Long> id) {
        if (CollectionUtil.isEmpty(id)) {
            return "";
        }
        return id.stream()
                 .map(Object::toString)
                 .collect(Collectors.joining(","));
    }

    public static List<Long> stringConvert(String ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }
        return Stream.of(ids.split(","))
                     .map(String::trim)
                     .map(Long::parseLong)
                     .collect(Collectors.toList());

    }
}
