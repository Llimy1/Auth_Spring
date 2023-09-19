package com.example.auth_spring.web.dto.signup;

import com.example.auth_spring.web.domain.user.User;
import lombok.Getter;

@Getter
public class SignupResponseDto {

    private Long userId;

    public SignupResponseDto(Long userId) {
        this.userId = userId;

    }
}
