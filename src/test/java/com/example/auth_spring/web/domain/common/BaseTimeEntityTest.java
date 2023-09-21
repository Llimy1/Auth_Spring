package com.example.auth_spring.web.domain.common;

import com.example.auth_spring.type.Role;
import com.example.auth_spring.web.domain.user.User;
import com.example.auth_spring.web.domain.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BaseTimeEntityTest {

    @Autowired
    private UserRepository userRepository;


    @AfterEach
    public void cleanup() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("[Domain]베이스 타임 적용 확인")
    void useBaseTimeEntity() {

        String email1 = "abce@naver.com";
        String password1 = "1234";
        String name1 = "홍길동";
        String nickname1 = "바람";
        String phoneNumber1 = "01000000000";
        String gender1 = "male";
        String introduce1 = "안녕하세요 홍길동 입니다.";
        String profileImgUrl1 = "https://img_url";
        Role role1 = Role.valueOf("USER");



        //given
        LocalDateTime now = LocalDateTime.now();
        userRepository.save(User.builder()
                .email(email1)
                .password(password1)
                .name(name1)
                .nickname(nickname1)
                .phoneNumber(phoneNumber1)
                .gender(gender1)
                .introduce(introduce1)
                .profileImgUrl(profileImgUrl1)
                .role(role1)
                .build());

        //when
        List<User> userList = userRepository.findAll();

        //then
        User user = userList.get(0);

        assertThat(user.getCreatedAt()).isAfter(now);
        assertThat(user.getModifiedAt()).isAfter(now);
    }

}