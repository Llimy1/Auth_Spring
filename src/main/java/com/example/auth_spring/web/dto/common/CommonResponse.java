package com.example.auth_spring.web.dto.common;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class CommonResponse<Data> {

    private String status;
    private String message;
    private HttpStatus httpStatus;
    private Data data;
}
