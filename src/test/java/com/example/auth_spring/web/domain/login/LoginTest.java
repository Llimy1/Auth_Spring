package com.example.auth_spring.web.domain.login;

import com.example.auth_spring.security.jwt.service.JwtProvider;
import com.example.auth_spring.type.Role;
import com.example.auth_spring.web.domain.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoginTest {

    @Autowired
    private JwtProvider jwtProvider;

    @Test
    @DisplayName("[Domain] 로그인 생성 도메인 테스트")
    void createLogin() {
        //given

        String email = "abce@naver.com";
        String password = "1234";
        String name = "홍길동";
        String nickname = "바람";
        String phoneNumber = "01000000000";
        String gender = "male";
        String introduce = "안녕하세요 홍길동 입니다.";
        String profileImgUrl = "https://img_url";
        Role role = Role.valueOf("USER");

        //given
        User user = User.builder()
                .email(email)
                .password(password)
                .name(name)
                .nickname(nickname)
                .phoneNumber(phoneNumber)
                .gender(gender)
                .introduce(introduce)
                .profileImgUrl(profileImgUrl)
                .role(role)
                .build();

        String refreshToken = jwtProvider.generateRefreshToken();

        Login login = Login.builder()
                .user(user)
                .refreshToken(refreshToken)
                .build();

        //when
        //then
        assertThat(user).isEqualTo(login.getUser());
        assertThat(refreshToken).isEqualTo(login.getRefreshToken());
    }
}