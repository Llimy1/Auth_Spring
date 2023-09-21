package com.example.auth_spring.service.auth.signup;

import com.example.auth_spring.web.domain.address.Address;
import com.example.auth_spring.web.domain.address.AddressRepository;
import com.example.auth_spring.web.domain.user.User;
import com.example.auth_spring.web.domain.user.UserRepository;
import com.example.auth_spring.web.dto.signup.SignupRequestDto;
import com.example.auth_spring.web.exception.IllegalStateException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SignupServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private SignupService signupService;


    @AfterEach
    public void cleanup() {
        userRepository.deleteAll();
        addressRepository.deleteAll();
    }

    @Test
    @DisplayName("[Service]회원가입 성공")
    void signupSuccess() throws Exception {

        //given
        SignupRequestDto signupRequestDto = signupRequestDto();
        User user = user(signupRequestDto);
        user.passwordEncode(passwordEncoder);

        Address address = address(signupRequestDto);


        given(userRepository.save(any()))
                .willReturn(user);

        given(addressRepository.save(any()))
                .willReturn(address);

        ReflectionTestUtils.setField(user, "id", 1L);

        //when
        Long userId = signupService.signup(signupRequestDto);

        //then
        verify(userRepository, times(1)).save(any());
        verify(addressRepository, times(1)).save(any());
        assertThat(userId).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("[Service]이미 존재하는 닉네임 오류 발생")
    void failNicknameExist() {

        //given
        User user = user(signupRequestDto());
        given(userRepository.findByNickname(user.getNickname()))
                .willReturn(Optional.of(user));


        //when
        //then
        assertThatThrownBy(() -> signupService.signup(signupRequestDto()))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("[Service]이미 존재하는 이메일 오류 발생")
    void failEmailExist() {

        //given
        User user = user(signupRequestDto());
        given(userRepository.findByEmail(user.getEmail()))
                .willReturn(Optional.of(user));



        //when
        //then
        assertThatThrownBy(() -> signupService.signup(signupRequestDto()))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("[Service]이미 존재하는 핸드폰 번호 오류 발생")
    void failPhoneNumberExist() {

        //given
        User user = user(signupRequestDto());
        given(userRepository.findByPhoneNumber(user.getPhoneNumber()))
                .willReturn(Optional.of(user));


        //when
        //then
        assertThatThrownBy(() -> signupService.signup(signupRequestDto()))
                .isInstanceOf(IllegalStateException.class);
    }

    private User user(SignupRequestDto signupRequestDto) {
        return signupRequestDto.toUserEntity();
    }

    private Address address(SignupRequestDto signupRequestDto) {
        return signupRequestDto.toAddressEntity(user(signupRequestDto));
    }


    private SignupRequestDto signupRequestDto() {
        String email = "abce@naver.com";
        String password = "1234";
        String name = "홍길동";
        String nickname = "바람";
        String phoneNumber = "01000000000";
        String gender = "male";
        String introduce = "안녕하세요 홍길동 입니다.";
        String profileImgUrl = "https://img_url";

        String zipCode = "12345";
        String streetAddress = "서울시 강남구";
        String detailAddress = "1길 30";


        return SignupRequestDto.builder()
                .email(email)
                .password(password)
                .name(name)
                .nickname(nickname)
                .phoneNumber(phoneNumber)
                .gender(gender)
                .introduce(introduce)
                .profileImgUrl(profileImgUrl)
                .zipCode(zipCode)
                .streetAddress(streetAddress)
                .detailAddress(detailAddress)
                .build();
    }
}