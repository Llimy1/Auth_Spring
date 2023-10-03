package com.example.auth_spring.service.all.signup.exist;

import com.example.auth_spring.service.all.signup.exist.SignupExistService;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.type.Role;
import com.example.auth_spring.web.domain.user.User;
import com.example.auth_spring.web.domain.user.UserRepository;
import com.example.auth_spring.web.exception.IllegalStateException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class SignupExistServiceTest {


    private UserRepository userRepository;

    private CommonService commonService;

    private SignupExistService signupExistService;

    private User user;

    String email = "abcd@naver.com";
    String password = "1234";
    String name = "홍길동";
    String nickname = "바람";
    String phoneNumber = "01000000000";
    String gender = "male";
    String introduce = "안녕하세요 홍길동 입니다.";
    String profileImgUrl = "https://img_url";
    Role role = Role.valueOf("USER");

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        signupExistService = new SignupExistService(userRepository, commonService);
        user = User.builder()
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
    }

    @AfterEach
    public void cleanup() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("[Service] 이미 존재하는 닉네임 오류 발생")
    void failNicknameExist() {

        //given
        given(userRepository.findByNickname(any()))
                .willReturn(Optional.of(user));

        //when
        //then
        assertThatThrownBy(() -> signupExistService.nicknameExist(nickname))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("[Service] 이미 존재하는 이메일 오류 발생")
    void failEmailExist() {

        //given
        given(userRepository.findByEmail(user.getEmail()))
                .willReturn(Optional.of(user));



        //when
        //then
        assertThatThrownBy(() -> signupExistService.emailExist(email))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("[Service] 이미 존재하는 핸드폰 번호 오류 발생")
    void failPhoneNumberExist() {


        //given
        given(userRepository.findByPhoneNumber(user.getPhoneNumber()))
                .willReturn(Optional.of(user));


        //when
        //then
        assertThatThrownBy(() -> signupExistService.phoneNumberExist(phoneNumber))
                .isInstanceOf(IllegalStateException.class);
    }

}