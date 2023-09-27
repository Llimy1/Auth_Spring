package com.example.auth_spring.web.dto.auth.login;

import com.example.auth_spring.web.domain.login.Login;
import com.example.auth_spring.web.domain.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BasicLoginRequestDto {

    @ApiModelProperty(name = "email", value = "login email", example = "abcd@naver.com")
    private String email;
    @ApiModelProperty(name = "password", value = "login password", example = "abcd1234")
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
