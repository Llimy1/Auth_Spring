package com.example.auth_spring.web.exception;

import com.example.auth_spring.type.ErrorCode;

public class NotFoundException extends RuntimeException {
    public NotFoundException(ErrorCode errorCode) {
        super(errorCode.getDescription());
    }
}
