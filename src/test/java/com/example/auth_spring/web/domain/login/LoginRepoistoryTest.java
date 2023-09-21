package com.example.auth_spring.web.domain.login;

import com.example.auth_spring.security.jwt.service.JwtProvider;
import com.example.auth_spring.type.Role;
import com.example.auth_spring.web.domain.user.User;
import com.example.auth_spring.web.domain.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoginRepoistoryTest {

    @Autowired
    private LoginRepoistory loginRepoistory;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @AfterEach
    public void cleanup() {
        loginRepoistory.deleteAll();
    }

    @Test
    @DisplayName("[Repository] 로그인 생성하기")
    void createLogin() {

        String email1 = "abce@naver.com";
        String password1 = "1234";
        String name1 = "홍길동";
        String nickname1 = "바람";
        String phoneNumber1 = "01000000000";
        String gender1 = "male";
        String introduce1 = "안녕하세요 홍길동 입니다.";
        String profileImgUrl1 = "https://img_url";
        Role role1 = Role.valueOf("USER");

        String email2 = "abced@naver.com";
        String password2 = "12345";
        String name2 = "김남수";
        String nickname2 = "남수";
        String phoneNumber2 = "01000000001";
        String gender2 = "male";
        String introduce2 = "안녕하세요 김남수 입니다.";
        String profileImgUrl2 = "https://img2_url";
        Role role2 = Role.valueOf("USER");

        //given
        User user1 = User.builder()
                .email(email1)
                .password(password1)
                .name(name1)
                .nickname(nickname1)
                .phoneNumber(phoneNumber1)
                .gender(gender1)
                .introduce(introduce1)
                .profileImgUrl(profileImgUrl1)
                .role(role1)
                .build();

        User user2 = User.builder()
                .email(email2)
                .password(password2)
                .name(name2)
                .nickname(nickname2)
                .phoneNumber(phoneNumber2)
                .gender(gender2)
                .introduce(introduce2)
                .profileImgUrl(profileImgUrl2)
                .role(role2)
                .build();

        User userSave1 = userRepository.save(user1);
        User userSave2 = userRepository.save(user2);


        String refreshToken1 = jwtProvider.generateRefreshToken(userSave1.getRoleKey());
        String refreshToken2 = jwtProvider.generateRefreshToken(userSave2.getRoleKey());

        Login login1 = Login.builder()
                .user(userSave1)
                .refreshToken(refreshToken1)
                .build();

        Login login2 = Login.builder()
                .user(userSave2)
                .refreshToken(refreshToken2)
                .build();




        Login result1 = loginRepoistory.save(login1);
        Login result2 = loginRepoistory.save(login2);

        //when
        //then
        assertThat(result1.getUser()).isEqualTo(login1.getUser());
        assertThat(result2.getUser()).isEqualTo(login2.getUser());
        assertThat(result1.getRefreshToken()).isEqualTo(login1.getRefreshToken());
        assertThat(result2.getRefreshToken()).isEqualTo(login2.getRefreshToken());
    }
}