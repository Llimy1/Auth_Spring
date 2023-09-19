package com.example.auth_spring.web.domain.user;

import com.example.auth_spring.web.domain.role.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class UserTest {

    @Test
    @DisplayName("[Domain]유저 생성 도메인 테스트")
    void createUser() {

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

        //when
        //then
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getPassword()).isEqualTo(password);
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getNickname()).isEqualTo(nickname);
        assertThat(user.getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(user.getGender()).isEqualTo(gender);
        assertThat(user.getIntroduce()).isEqualTo(introduce);
        assertThat(user.getProfileImgUrl()).isEqualTo(profileImgUrl);
        assertThat(user.getRole()).isEqualTo(role);
    }

}