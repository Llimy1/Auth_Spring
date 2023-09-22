package com.example.auth_spring.service.auth.login;

import com.example.auth_spring.security.jwt.dto.GeneratedTokenDto;
import com.example.auth_spring.security.jwt.service.JwtProvider;
import com.example.auth_spring.type.Role;
import com.example.auth_spring.web.domain.login.Login;
import com.example.auth_spring.web.domain.login.LoginRepository;
import com.example.auth_spring.web.domain.user.User;
import com.example.auth_spring.web.domain.user.UserRepository;
import com.example.auth_spring.web.dto.auth.login.LoginReqeustDto;
import com.example.auth_spring.web.exception.LoginException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BasicLoginServiceTest {


    @Mock
    private UserRepository userRepository;

    @Mock
    private LoginRepository loginRepository;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private BasicLoginService basicLoginService;

    @AfterEach
    public void cleanup() {
        userRepository.deleteAll();
        loginRepository.deleteAll();
    }

    String email = "abcd@naver.com";
    String password = "1234";
    String name = "홍길동";
    String nickname = "바람";
    String phoneNumber = "01000000000";
    String gender = "male";
    String introduce = "안녕하세요 홍길동 입니다.";
    String profileImgUrl = "https://img_url";
    Role role = Role.valueOf("USER");

    @Test
    @DisplayName("[Service] 로그인 성공")
    void loginSuccess() {
        //given

        LoginReqeustDto loginReqeustDto = loginReqeustDto();


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

        user.passwordEncode(passwordEncoder);


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginReqeustDto.getEmail(), loginReqeustDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);


        given(userRepository.findByEmail(anyString()))
                .willReturn(Optional.of(user));

        String accessToken = jwtProvider.generateAccessToken(user.getEmail(), user.getRoleKey());
        String refreshToken = jwtProvider.generateRefreshToken(user.getRoleKey());

        Login login = loginReqeustDto.toEntity(user, refreshToken);

        given(loginRepository.save(any()))
                .willReturn(login);


        //when
        GeneratedTokenDto token = basicLoginService.basicLogin(loginReqeustDto);


        //then
        assertThat(accessToken).isEqualTo(token.getAccessToken());
        assertThat(token.getRefreshToken()).isEqualTo(login.getRefreshToken());
        assertThat(user).isEqualTo(login.getUser());
    }

    @Test
    @DisplayName("[Service] 이메일 불일치")
    void loginEmailFail() {
        //given
        LoginReqeustDto loginReqeustDto = loginReqeustDto();


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

        user.passwordEncode(passwordEncoder);


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken("abc@naver.com", loginReqeustDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        //when
        //then
        assertThatThrownBy(() -> basicLoginService.basicLogin(loginReqeustDto()))
                .isInstanceOf(LoginException.class);
    }

    @Test
    @DisplayName("[Service] 패스워드 불일치")
    void loginPasswordFail() {
        //given
        LoginReqeustDto loginReqeustDto = loginReqeustDto();


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

        user.passwordEncode(passwordEncoder);


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginReqeustDto.getEmail(), "123"));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        //when
        //then
        assertThatThrownBy(() -> basicLoginService.basicLogin(loginReqeustDto()))
                .isInstanceOf(LoginException.class);
    }


    private LoginReqeustDto loginReqeustDto() {
        String email = "abcd@naver.com";
        String password = "1234";

        return LoginReqeustDto.builder()
                .email(email)
                .password(password)
                .build();
    }
}