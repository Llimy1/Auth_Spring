package com.example.auth_spring.security.config.handler;

import com.example.auth_spring.security.jwt.service.JwtProvider;
import com.example.auth_spring.service.auth.login.OAuth2LoginService;
import com.example.auth_spring.type.ErrorCode;
import com.example.auth_spring.type.Role;
import com.example.auth_spring.web.exception.IllegalAccessException;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final OAuth2LoginService oAuth2LoginService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();


        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String profileImgUrl = oAuth2User.getAttribute("profileImgUrl");
        String provider = oAuth2User.getAttribute("provider");


        String role = oAuth2User.getAuthorities().stream()
                .findFirst().orElseThrow(() -> new IllegalAccessException(ErrorCode.AUTHORITY_NOT_FOUND))
                .getAuthority();


        if (Objects.equals(role, Role.GUEST.getKey())) {

            response.sendRedirect("http://localhost:8080/signup.html");


        } else {
            oAuth2LoginService.oAuth2LoginResponse(email, response);
            response.sendRedirect("http://localhost:8080/loginsuccess.html");
        }
    }
}
