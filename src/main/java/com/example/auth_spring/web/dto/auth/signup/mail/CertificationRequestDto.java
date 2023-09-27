package com.example.auth_spring.web.dto.auth.signup.mail;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CertificationRequestDto {
    @ApiModelProperty(name = "email", value = "email", example = "abcd@naver.com")
    private String email;

    public CertificationRequestDto(String email) {
        this.email = email;
    }
}
