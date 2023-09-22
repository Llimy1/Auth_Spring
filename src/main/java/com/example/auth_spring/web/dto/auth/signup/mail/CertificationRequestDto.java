package com.example.auth_spring.web.dto.auth.signup.mail;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CertificationRequestDto {
    private String email;

    public CertificationRequestDto(String email) {
        this.email = email;
    }
}
