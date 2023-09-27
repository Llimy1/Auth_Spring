package com.example.auth_spring.web.dto.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class UserIdResponseDto {

    @ApiModelProperty(example = "해당 유저 아이디")
    private Long userId;

    public UserIdResponseDto(Long userId) {
        this.userId = userId;

    }
}
