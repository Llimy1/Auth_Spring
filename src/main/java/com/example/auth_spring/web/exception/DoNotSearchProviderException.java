package com.example.auth_spring.web.exception;

import com.example.auth_spring.type.ErrorCode;

public class DoNotSearchProviderException extends RuntimeException {
    public DoNotSearchProviderException(ErrorCode errorCode) {
        super(errorCode.getDescription());
    }
}
