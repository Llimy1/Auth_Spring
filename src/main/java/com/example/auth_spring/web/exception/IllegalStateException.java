package com.example.auth_spring.web.exception;


import com.example.auth_spring.type.ErrorCode;

public class IllegalStateException extends RuntimeException {

    public IllegalStateException(ErrorCode errorCode) {
        super(errorCode.getDescription());
    }
}
