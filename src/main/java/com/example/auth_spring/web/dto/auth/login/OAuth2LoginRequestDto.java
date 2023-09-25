package com.example.auth_spring.web.dto.auth.login;

import com.example.auth_spring.web.domain.login.Login;
import com.example.auth_spring.web.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OAuth2LoginRequestDto {
    private String email;

    @Builder
    public OAuth2LoginRequestDto(String email) {
        this.email = email;
    }

    public Login toEntity(User user, String refreshToken) {
        return Login.builder()
                .user(user)
                .refreshToken(refreshToken)
                .build();
    }
}
