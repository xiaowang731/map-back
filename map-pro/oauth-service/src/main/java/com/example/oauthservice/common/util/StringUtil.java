package com.example.oauthservice.common.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.ThreadLocalRandom;

public final class StringUtil {
    private static final String s = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_";

    private StringUtil() {
        throw new UnsupportedOperationException();
    }

    public static String createRandomString(int length) {
        ThreadLocalRandom random  = ThreadLocalRandom.current();
        StringBuilder     builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(s.charAt(random.nextInt(s.length())));
        }
        return builder.toString();
    }

    public static String bitsOf(int num) {
        StringBuilder res = new StringBuilder();
        for (int i = 31; i >= 0; i -= 1) {
            res.append(((num & (1 << i)) == 0) ? "0" : "1");
        }
        return res.toString();
    }

    public static LocalDateTime longToDate(Long time) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
    }
}
