package com.example.j.common.exception;


import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class JException extends RuntimeException {
    private Integer code;

    public JException() {
    }

    public JException(String message) {
        super(message);
    }

    public JException(Integer code, String message) {
        super(message);
        this.code = code;
    }

}
