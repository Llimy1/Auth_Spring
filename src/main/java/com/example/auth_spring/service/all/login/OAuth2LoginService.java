package com.example.auth_spring.service.all.login;

import com.example.auth_spring.security.jwt.dto.GeneratedTokenDto;
import com.example.auth_spring.security.jwt.service.JwtProvider;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.type.ErrorCode;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.domain.login.Login;
import com.example.auth_spring.web.domain.login.LoginRepository;
import com.example.auth_spring.web.domain.user.User;
import com.example.auth_spring.web.domain.user.UserRepository;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.exception.LoginException;
import com.example.auth_spring.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Service
public class OAuth2LoginService {

    private final UserRepository userRepository;
    private final LoginRepository loginRepository;

    private final JwtProvider jwtProvider;
    private final CommonService commonService;


    // OAuth2 로그인
    @Transactional
    public GeneratedTokenDto oauth2Login(String email) {

        try {

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

            String accessToken = jwtProvider.generateAccessToken(user.getEmail(), user.getRoleKey());
            String refreshToken = jwtProvider.generateRefreshToken();

            Login login = Login.builder()
                    .user(user)
                    .refreshToken(refreshToken)
                    .build();

            loginRepository.save(login);

            return GeneratedTokenDto.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();

        } catch (Exception e) {
            throw new LoginException(ErrorCode.LOGIN_EXCEPTION);
        }
    }

    // API 반환
    @Transactional
    public CommonResponse<Object> oAuth2LoginResponse(String email) {

        return commonService.successResponse(SuccessCode.OAUTH2_LOGIN_SUCCESS.getDescription(), HttpStatus.CREATED, oauth2Login(email));
    }


}
