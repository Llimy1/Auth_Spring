package com.example.auth_spring.web.dto.auth.signup.mail;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class CertificationResponseDto {
    @ApiModelProperty(name = "code", value = "인증 번호")
    private String code;

    public CertificationResponseDto(String code) {
        this.code = code;
    }
}
