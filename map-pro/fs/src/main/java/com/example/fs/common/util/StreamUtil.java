package com.example.fs.common.util;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public final class StreamUtil {
    private StreamUtil() {
        throw new UnsupportedOperationException();
    }

    public static <T> Predicate<T> distinctByKeyOrNull(Function<? super T, ?> keyProvider) {
        Set<Object> visited = new HashSet<>();
        return e -> {
            Object k = keyProvider.apply(e);
            if (k == null) {
                return false;
            }
            return visited.add(k);
        };
    }
}
