package com.example.auth_spring.web.domain.address;

import com.example.auth_spring.web.domain.role.Role;
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
class AddressRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @AfterEach
    public void cleanup() {
        userRepository.deleteAll();
        addressRepository.deleteAll();
    }

    @Test
    @DisplayName("주소 생성하기")
    void createAddress() {
        String mainAddress1 = "서울시 강남구";
        String detailAddress1 = "1길 30";

        String mainAddress2 = "서울시 강남구";
        String deatilAddress2 = "1길 50";

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

        User user1 = userRepository.save(User.builder()
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

        User user2 = userRepository.save(User.builder()
                .email(email2)
                .password(password2)
                .name(name2)
                .nickname(nickname2)
                .phoneNumber(phoneNumber2)
                .gender(gender2)
                .introduce(introduce2)
                .profileImgUrl(profileImgUrl2)
                .role(role2)
                .build());




        //given
        Address address1 = Address.builder()
                .mainAddress(mainAddress1)
                .detailAddress(detailAddress1)
                .user(user1)
                .build();

        Address address2 = Address.builder()
                .mainAddress(mainAddress2)
                .detailAddress(deatilAddress2)
                .user(user2)
                .build();


        //when
        Address result1 = addressRepository.save(address1);
        Address result2 = addressRepository.save(address2);


        //then
        assertThat(result1.getMainAddress()).isEqualTo(mainAddress1);
        assertThat(result2.getMainAddress()).isEqualTo(mainAddress2);
    }
}