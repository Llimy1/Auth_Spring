package com.example.auth_spring.service.auth.login;

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
import com.example.auth_spring.web.dto.auth.login.LoginReqeustDto;
import com.example.auth_spring.web.exception.LoginException;
import com.example.auth_spring.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BasicLoginService {

    private final UserRepository userRepository;
    private final LoginRepository loginRepository;

    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final CommonService commonService;



    // 자체 로그인
    @Transactional
    public GeneratedTokenDto basicLogin(LoginReqeustDto loginReqeustDto) {

        String requestEmail = loginReqeustDto.getEmail();
        String requestPassword = loginReqeustDto.getPassword();

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestEmail, requestPassword));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = userRepository.findByEmail(loginReqeustDto.getEmail())
                    .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));


            String accessToken = jwtProvider.generateAccessToken(user.getEmail(), user.getRoleKey());
            String refreshToken = jwtProvider.generateRefreshToken(user.getRoleKey());

            Login login = loginReqeustDto.toEntity(user, refreshToken);
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
    public CommonResponse<Object> basicLoginResponse(LoginReqeustDto loginReqeustDto) {
        GeneratedTokenDto generatedTokenDto = basicLogin(loginReqeustDto);

        return commonService.successResponse(SuccessCode.BASIC_LOGIN_SUCCESS.getDescription(), HttpStatus.CREATED, generatedTokenDto);
    }
}
