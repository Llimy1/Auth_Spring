package com.example.auth_spring.web.dto.login;

import com.example.auth_spring.web.domain.login.Login;
import com.example.auth_spring.web.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginReqeustDto {

    private String email;
    private String password;

    @Builder
    public LoginReqeustDto(String email, String password) {
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
