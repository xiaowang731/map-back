package com.example.fs.common.util;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.fs.common.exception.ParameterException;

import java.util.Objects;

public final class Assert {
    private Assert() {
        throw new UnsupportedOperationException();
    }

    public static void isTrue(Boolean b, String message) {
        if (b != null && b) {
            return;
        }
        throw new ParameterException(message);
    }

    public static void isFalse(Boolean b, String message) {
        if (b) {
            throw new ParameterException(message);
        }
    }

    /**
     * 同 {@link #isTrue(Boolean, String)}
     *
     * @author Howie Young
     */
    public static void must(Boolean b, String message) {
        if (b != null && b) {
            return;
        }
        throw new ParameterException(message);
    }

    /**
     * 同 {@link #isFalse(Boolean, String)}
     *
     * @author Howie Young
     */
    public static void not(Boolean b, String message) {
        if (b) {
            throw new ParameterException(message);
        }
    }

    public static void isNull(Object o, String message) {
        if (o != null) {
            throw new ParameterException(message);
        }
    }

    public static void notNull(Object o, String message) {
        if (o == null) {
            throw new ParameterException(message);
        }
    }

    public static void notNull(Object o, String message, Object data) {
        if (o == null) {
            throw new ParameterException(message).data(data);
        }
    }

    public static void equal(Object o1, Object o2, String message) {
        if (Objects.equals(o1, o2)) {
            return;
        }
        throw new ParameterException(message);
    }

    public static void hasText(String s, String message) {
        if (StringUtils.isBlank(s)) {
            throw new ParameterException(message);
        }
    }
}
