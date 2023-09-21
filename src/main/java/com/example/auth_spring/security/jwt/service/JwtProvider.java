package com.example.auth_spring.security.jwt.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtProvider implements InitializingBean {

    private final UserDetailsService userDetailsService;

    @Value("${jwt.secret}")
    private String secret;
    private Key signingKey;

    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String ROLE_KEY = "role";
    private static final String ACCESS_TOKEN_KEY = "Authorization";
    private static final String REFRESH_TOKEN_KEY = "RefreshToken";

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] secretKeyByte = secret.getBytes();
        signingKey = Keys.hmacShaKeyFor(secretKeyByte);
    }


    public String generateAccessToken(String email, String role) {
        long tokenPeriod = 1000L * 60L * 30L; // 30분

        // 새로운 클레임 객체를 생성, 이메일과 역할 (권한) 설정
        Claims claims = Jwts.claims().setSubject(email);
        claims.setSubject(ACCESS_TOKEN_KEY);
        claims.put(ROLE_KEY, role);

        Date now = new Date();

        return Jwts.builder()
                // Payload를 구성하는 속성들 정의
                .setClaims(claims)
                // 발행 일자
                .setIssuedAt(now)
                // 토큰 만료 일시
                .setExpiration(new Date(now.getTime() + tokenPeriod))
                // 저장된 서명 알고리즘과 비밀 키로 토큰 서명
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(String role) {
        long refreshPeriod = 1000L * 60L * 60L * 24L * 14L; // 2주
        Claims claims = Jwts.claims().setSubject(REFRESH_TOKEN_KEY);
        claims.put(ROLE_KEY, role);

        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshPeriod))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }


    // 유효 토큰 확인
    public Boolean verifyToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(signingKey) // 비밀 키 설정하여 파싱
                    .build().parseClaimsJws(token); // 주어진 토큰 파싱하여 claims 객체 빼오기

            // 토큰 만료 기간과 현재 시간 비교
            return claims.getBody()
                    .getExpiration()
                    .after(new Date());  // 만료 시간이 현재 시간 이후인지 확인하여 유효성 결과를 반환
        } catch (Exception e) {
            return false;
        }
    }

    // 토큰 값만 추출
    public String resolveToken(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader("Autho    rization");
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    // 권한 생성
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 이메일 추출
    public String getEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build().parseClaimsJws(token)
                .getBody().getSubject();
    }

    // 토큰에서 권한 추출
    public String getTokenRole (String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build().parseClaimsJws(token)
                .getBody().get(ROLE_KEY, String.class);
    }

}
