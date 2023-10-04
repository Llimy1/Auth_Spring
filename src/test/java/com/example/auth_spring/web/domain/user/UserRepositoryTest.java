package com.example.auth_spring.web.domain.user;

import com.example.auth_spring.type.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;




@SpringBootTest
@Transactional
class UserRepositoryTest {


    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("[Repository] 유저 생성하기")
    void createUser() {

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

        //when
        User result1 = userRepository.save(user1);
        User result2 = userRepository.save(user2);

        //then
        assertThat(result1.getName()).isEqualTo(name1);
        assertThat(result2.getName()).isEqualTo(name2);
    }
}