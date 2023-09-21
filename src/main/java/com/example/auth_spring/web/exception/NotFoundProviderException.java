package com.example.auth_spring.web.exception;

import com.example.auth_spring.type.ErrorCode;

public class NotFoundProviderException extends RuntimeException {
    public NotFoundProviderException(ErrorCode errorCode) {
        super(errorCode.getDescription());
    }
}
