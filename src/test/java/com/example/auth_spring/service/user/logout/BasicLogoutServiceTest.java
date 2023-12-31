package com.example.auth_spring.service.user.logout;

import com.example.auth_spring.security.jwt.service.JwtProvider;
import com.example.auth_spring.service.user.token.TokenService;
import com.example.auth_spring.service.user.logout.BasicLogoutService;
import com.example.auth_spring.type.Role;
import com.example.auth_spring.web.domain.login.Login;
import com.example.auth_spring.web.domain.login.LoginRepository;
import com.example.auth_spring.web.domain.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BasicLogoutServiceTest {
    @Mock
    private LoginRepository loginRepository;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private BasicLogoutService basicLogoutService;

    @AfterEach
    public void cleanup() {
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
    @DisplayName("[Service] 로그아웃 성공")
    void logoutSuccess() {
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

        user.passwordEncode(passwordEncoder);
        
        Login login = Login.builder()
                .user(user)
                .refreshToken("refreshToken")
                .build();

        String accessToken = jwtProvider.generateAccessToken(user.getEmail(), user.getRoleKey());

        given(loginRepository.findByUserId(any()))
                .willReturn(Optional.of(login));

        //when
        basicLogoutService.logout(accessToken);

        //then
        assertThat(loginRepository.findByRefreshToken(login.getRefreshToken())).isEmpty();
    }
}