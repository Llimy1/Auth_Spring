package com.example.auth_spring.web.dto.signup.mail;

import lombok.Getter;

@Getter
public class CertificationResponseDto {
    private String code;

    public CertificationResponseDto(String code) {
        this.code = code;
    }
}
