package com.example.auth_spring.web.domain.address;

import com.example.auth_spring.web.domain.role.Role;
import com.example.auth_spring.web.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class AddressTest {

    @Test
    @DisplayName("주소 생성 도메인 테스트")
    void createAddress() {

        String mainAddress = "서울시 강남구";
        String detailAddress = "1길 30";
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

        //given
        Address address = Address.builder()
                .mainAddress(mainAddress)
                .detailAddress(detailAddress)
                .user(user)
                .build();

        //when
        //then

        assertThat(address.getMainAddress()).isEqualTo(mainAddress);
        assertThat(address.getDetailAddress()).isEqualTo(detailAddress);
        assertThat(address.getUser()).isEqualTo(user);
    }
}