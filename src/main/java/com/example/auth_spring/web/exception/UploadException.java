package com.example.auth_spring.web.exception;

import com.example.auth_spring.type.ErrorCode;

public class UploadException extends RuntimeException {
    public UploadException(ErrorCode errorCode) {
        super(errorCode.getDescription());
    }
}
