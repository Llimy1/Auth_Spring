package com.example.auth_spring.security.jwt.service;

import com.example.auth_spring.security.jwt.dto.GeneratedTokenDto;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.type.ErrorCode;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.domain.login.Login;
import com.example.auth_spring.web.domain.login.LoginRepository;
import com.example.auth_spring.web.domain.user.User;
import com.example.auth_spring.web.domain.user.UserRepository;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.exception.ExpirationFiveMinutesException;
import com.example.auth_spring.web.exception.JwtException;
import com.example.auth_spring.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;


@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtProvider jwtProvider;
    private final LoginRepository loginRepository;
    private final UserRepository userRepository;
    private final CommonService commonService;

    private static final String TOKEN_PREFIX = "Bearer ";

    // Refresh Token으로 AccessToken과 RefreshToken 재발급
    @Transactional
    public GeneratedTokenDto reissue(String bearerRefreshToken) {

        String refreshToken = resolveToken(bearerRefreshToken);


        if (!jwtProvider.verifyToken(refreshToken)) {
            throw new JwtException(ErrorCode.REFRESH_TOKEN_EXPIRATION);
        }

        Login login = loginRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new NotFoundException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));

        Long userId = login.getUser().getId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        String email = user.getEmail();
        String role = user.getRoleKey();

        String reissueAccessToken = jwtProvider.generateAccessToken(email, role);
        String reissueRefreshToken = jwtProvider.generateRefreshToken();

        login.updateRefreshToken(reissueRefreshToken);


        return GeneratedTokenDto.builder()
                .accessToken(reissueAccessToken)
                .refreshToken(reissueRefreshToken)
                .build();
    }

    // API 반환
    @Transactional
    public CommonResponse<Object> reissueResponse(String refreshToken, HttpServletResponse httpServletResponse) {
        GeneratedTokenDto generatedTokenDto = reissue(refreshToken);

        httpServletResponse.setHeader("Authorization", generatedTokenDto.getAccessToken());
        httpServletResponse.setHeader("REFRESH-TOKEN", generatedTokenDto.getRefreshToken());

        return commonService.successResponse(SuccessCode.TOKEN_REISSUE_SUCCESS.getDescription(), HttpStatus.OK, null);
    }

    // 토큰 값만 추출
    public String resolveToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    // AccessToken 만료 시간이 5분보다 적게 남았을 때
    public void accessTokenExpiration(String accessToken) {
        String resolveAccessToken = resolveToken(accessToken);

        if (jwtProvider.getExpiration(resolveAccessToken)) {
            throw new ExpirationFiveMinutesException(ErrorCode.ACCESS_TOKEN_FIVE_MINUTES);
        }
    }

    // AccessToken에서 userId 가져오기
    public Long accessTokenUserId(String accessToken) {
        String resolveAccessToken = resolveToken(accessToken);

        String email = jwtProvider.getEmail(resolveAccessToken);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        return user.getId();
    }

    // AccessToken을 통해 user 가져오기
    public User findUser(String accessToken) {
        String resolveAccessToken = resolveToken(accessToken);

        String email = jwtProvider.getEmail(resolveAccessToken);

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }
}
