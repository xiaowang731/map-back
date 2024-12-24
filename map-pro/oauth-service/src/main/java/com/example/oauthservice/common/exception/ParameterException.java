package com.example.oauthservice.common.exception;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ParameterException extends JException {
    private Object data;

    public ParameterException() {
        super();
    }

    public ParameterException(String message) {
        super(message);
    }

    public ParameterException(Integer code, String message) {
        super(code, message);
    }

    @Override
    public Integer getCode() {
        Integer code = super.getCode();
        if (code == null) {
            code = 400;
        }
        return code;
    }

    @Override
    public String getMessage() {
        String message = super.getMessage();
        if (message == null) {
            message = "参数错误";
        }
        return message;
    }

    public ParameterException data(Object data) {
        this.data = data;
        return this;
    }
}
