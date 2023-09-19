package com.example.auth_spring.web.dto.signup.mail;

import lombok.Getter;

@Getter
public class MailResponseDto {
    private String code;

    public MailResponseDto(String code) {
        this.code = code;
    }
}
