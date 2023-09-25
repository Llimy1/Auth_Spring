package com.example.auth_spring.web.dto.auth.login;

import com.example.auth_spring.web.domain.login.Login;
import com.example.auth_spring.web.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BasicLoginRequestDto {

    private String email;
    private String password;

    @Builder
    public BasicLoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Login toEntity(User user, String refreshToken) {
        return Login.builder()
                .user(user)
                .refreshToken(refreshToken)
                .build();
    }
}
