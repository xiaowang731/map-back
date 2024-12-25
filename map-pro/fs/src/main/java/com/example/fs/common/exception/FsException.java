package com.example.fs.common.exception;

/**
 * @author Howie Young
 * @date 2023/05/11 15:00
 */
public class FsException extends RuntimeException {
    private Integer code;

    public FsException() {
    }

    public FsException(String message) {
        super(message);
    }

    public FsException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
