package com.example.auth_spring.security.jwt.filter;

import com.example.auth_spring.security.jwt.service.JwtProvider;
import com.example.auth_spring.type.ErrorCode;
import com.example.auth_spring.web.exception.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Value("${jwt.secret}")
    private String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // request Header에서 accessToken을 가져온다.
        String accessToken = jwtProvider.resolveToken(request);

        // 토큰 검사 생략 (모두 허용 URL인 경우 토큰 검사 통과)
        if (!StringUtils.hasText(accessToken)) {
            doFilter(request, response, filterChain);
            return;
        }

        // AccessToken을 검증하고 만료 되었을 경우 예외 발생
        if (!jwtProvider.verifyToken(accessToken)) {
            throw new JwtException(ErrorCode.ACCESS_TOKEN_EXPIRATION);
        }

        // AccessToken 값이 있고, 유효한 경우에만 진행
        if (jwtProvider.verifyToken(accessToken)) {
            // AccessToken 내부 payload에 있는 email로 user를 찾는다 -> 없다면 정상 토큰이 아님 (오류 반환)
            Authentication authentication = jwtProvider.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
