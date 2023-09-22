package com.example.auth_spring.web.dto.auth.signup.mail;

import lombok.Getter;

@Getter
public class CertificationResponseDto {
    private String code;

    public CertificationResponseDto(String code) {
        this.code = code;
    }
}
