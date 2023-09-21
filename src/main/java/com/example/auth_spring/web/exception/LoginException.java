package com.example.auth_spring.web.exception;

import com.example.auth_spring.type.ErrorCode;

public class LoginException extends RuntimeException {
    public LoginException(ErrorCode errorCode) {
        super(errorCode.getDescription());
    }
}
