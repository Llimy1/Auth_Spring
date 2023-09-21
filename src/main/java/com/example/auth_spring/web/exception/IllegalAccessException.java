package com.example.auth_spring.web.exception;

import com.example.auth_spring.type.ErrorCode;

public class IllegalAccessException extends RuntimeException {
    public IllegalAccessException(ErrorCode errorCode) {
        super(errorCode.getDescription());
    }
}
