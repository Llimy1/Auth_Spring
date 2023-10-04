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
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class LoginRepositoryTest {

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;



    @Test
    @DisplayName("[Repository] 로그인 생성하기")
    void createLogin() {

        String email1 = "a";
        String password1 = "b";
        String name1 = "c";
        String nickname1 = "d";
        String phoneNumber1 = "e";
        String gender1 = "f";
        String introduce1 = "g";
        String profileImgUrl1 = "h";
        Role role1 = Role.valueOf("USER");

        String email2 = "i";
        String password2 = "j";
        String name2 = "k";
        String nickname2 = "l";
        String phoneNumber2 = "m";
        String gender2 = "n";
        String introduce2 = "o";
        String profileImgUrl2 = "p";
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


        String refreshToken1 = jwtProvider.generateRefreshToken();
        String refreshToken2 = jwtProvider.generateRefreshToken();

        Login login1 = Login.builder()
                .user(userSave1)
                .refreshToken(refreshToken1)
                .build();

        Login login2 = Login.builder()
                .user(userSave2)
                .refreshToken(refreshToken2)
                .build();




        Login result1 = loginRepository.save(login1);
        Login result2 = loginRepository.save(login2);

        //when
        //then
        assertThat(result1.getUser()).isEqualTo(login1.getUser());
        assertThat(result2.getUser()).isEqualTo(login2.getUser());
        assertThat(result1.getRefreshToken()).isEqualTo(login1.getRefreshToken());
        assertThat(result2.getRefreshToken()).isEqualTo(login2.getRefreshToken());
    }
}