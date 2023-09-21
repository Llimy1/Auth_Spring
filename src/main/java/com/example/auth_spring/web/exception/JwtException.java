package com.example.auth_spring.web.exception;

import com.example.auth_spring.type.ErrorCode;

public class JwtException extends RuntimeException {
    public JwtException(ErrorCode errorCode) {
        super(errorCode.getDescription());
    }
}
