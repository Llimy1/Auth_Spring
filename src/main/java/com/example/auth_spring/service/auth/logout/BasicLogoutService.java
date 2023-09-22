package com.example.auth_spring.service.auth.logout;

import com.example.auth_spring.security.jwt.service.JwtProvider;
import com.example.auth_spring.security.jwt.service.TokenService;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.type.ErrorCode;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.domain.login.Login;
import com.example.auth_spring.web.domain.login.LoginRepository;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.exception.JwtException;
import com.example.auth_spring.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BasicLogoutService {

    private final JwtProvider jwtProvider;
    private final TokenService tokenService;
    private final CommonService commonService;

    private final LoginRepository loginRepository;

    @Transactional
    public void logout(String accessToken) {

        if (jwtProvider.verifyToken(accessToken)) {
            throw new JwtException(ErrorCode.ACCESS_TOKEN_EXPIRATION);
        }

        Long userId = tokenService.accessTokenUserId(accessToken);

        Login login = loginRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.LOGIN_INFO_NOT_FOUND));

        loginRepository.delete(login);
    }

    @Transactional
    public CommonResponse<Object> logoutResponse(String accessToken) {
        logout(accessToken);

        return commonService.successResponse(SuccessCode.BASIC_LOGOUT_SUCCESS.getDescription(), HttpStatus.OK, null);
    }
}
