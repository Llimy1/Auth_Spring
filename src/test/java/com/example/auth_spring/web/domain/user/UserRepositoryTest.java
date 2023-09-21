package com.example.auth_spring.web.domain.user;

import com.example.auth_spring.type.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;




@SpringBootTest
class UserRepositoryTest {


    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void afterCleanup() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("[Repository]유저 생성하기")
    void createUser() {

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

        //when
        User result1 = userRepository.save(user1);
        User result2 = userRepository.save(user2);

        //then
        assertThat(result1.getName()).isEqualTo(name1);
        assertThat(result2.getName()).isEqualTo(name2);
    }
}