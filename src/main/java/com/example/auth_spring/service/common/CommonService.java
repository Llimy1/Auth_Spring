package com.example.auth_spring.service.common;

import com.example.auth_spring.type.ResponseStatus;
import com.example.auth_spring.web.dto.common.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CommonService {

    public CommonResponse<Object> successResponse(String message, HttpStatus httpStatus, Object data) {
        return CommonResponse.builder()
                .httpStatus(httpStatus)
                .status(ResponseStatus.SUCCESS.getDescription())
                .message(message)
                .data(data)
                .build();
    }

    public CommonResponse<Object> errorResponse(String message, HttpStatus httpStatus, Object data) {
        return CommonResponse.builder()
                .httpStatus(httpStatus)
                .status(ResponseStatus.FAIL.getDescription())
                .message(message)
                .data(data)
                .build();
    }
}
