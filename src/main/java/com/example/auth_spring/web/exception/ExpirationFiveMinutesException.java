package com.example.auth_spring.web.exception;

import com.example.auth_spring.type.ErrorCode;

public class ExpirationFiveMinutesException extends RuntimeException {
    public ExpirationFiveMinutesException(ErrorCode errorCode) {
        super(errorCode.getDescription());
    }
}
