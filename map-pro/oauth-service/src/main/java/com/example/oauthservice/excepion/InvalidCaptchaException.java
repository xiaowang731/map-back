package com.example.oauthservice.excepion;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Adss
 * @data 2024/12/26 14:09
 */
public class InvalidCaptchaException extends AuthenticationException {

    public InvalidCaptchaException(String msg) {
        super(msg);
    }

}
