package com.example.auth_spring.web.dto.signup.mail;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MailRequestDto {
    private String email;

    public MailRequestDto(String email) {
        this.email = email;
    }
}
