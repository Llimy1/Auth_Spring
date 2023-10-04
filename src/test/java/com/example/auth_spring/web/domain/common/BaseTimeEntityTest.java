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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class BaseTimeEntityTest {

    @Autowired
    private UserRepository userRepository;




    @Test
    @DisplayName("[Domain] 베이스 타임 적용 확인")
    void useBaseTimeEntity() {

        String email1 = "a";
        String password1 = "b";
        String name1 = "c";
        String nickname1 = "d";
        String phoneNumber1 = "e";
        String gender1 = "f";
        String introduce1 = "g";
        String profileImgUrl1 = "h";
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