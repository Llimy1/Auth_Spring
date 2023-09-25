package com.example.auth_spring.web.controller.auth.login;

import com.example.auth_spring.security.jwt.dto.GeneratedTokenDto;
import com.example.auth_spring.service.auth.login.BasicLoginService;
import com.example.auth_spring.service.auth.login.OAuth2LoginService;
import com.example.auth_spring.web.dto.auth.login.BasicLoginRequestDto;
import com.example.auth_spring.web.dto.auth.login.OAuth2LoginRequestDto;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.ResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class LoginController {

    private final BasicLoginService basicLoginService;
    private final OAuth2LoginService oAuth2LoginService;

    @PostMapping("/login/basic")
    public ResponseEntity<ResultDto<Void>> basicLogin(@RequestBody BasicLoginRequestDto basicLoginRequestDto, HttpServletResponse httpServletResponse) {
        CommonResponse<Object> commonResponse = basicLoginService.basicLoginResponse(basicLoginRequestDto, httpServletResponse);
        ResultDto<Void> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());

        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }

    @GetMapping("/login/oath2")
    public ResponseEntity<ResultDto<Void>> oauth2Login(@RequestParam String email, HttpServletResponse httpServletResponse) {
        CommonResponse<Object> commonResponse = oAuth2LoginService.oAuth2LoginResponse(email, httpServletResponse);
        ResultDto<Void> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());

        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }
}
