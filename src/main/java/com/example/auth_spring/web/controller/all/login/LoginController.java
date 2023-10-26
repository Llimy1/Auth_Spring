package com.example.auth_spring.web.controller.all.login;

import com.example.auth_spring.security.jwt.dto.GeneratedTokenDto;
import com.example.auth_spring.service.all.login.BasicLoginService;
import com.example.auth_spring.service.all.login.OAuth2LoginService;
import com.example.auth_spring.web.dto.auth.login.BasicLoginRequestDto;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.ResultDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/all")
@Api(tags = "Auth APIs")
public class LoginController {

    private final BasicLoginService basicLoginService;
    private final OAuth2LoginService oAuth2LoginService;

    @ApiOperation(value = "자체 로그인 API", notes = "자체 로그인 진행")
    @PostMapping("/login/basic")
    public ResponseEntity<ResultDto<GeneratedTokenDto>> basicLogin(@RequestBody BasicLoginRequestDto basicLoginRequestDto) {
        CommonResponse<Object> commonResponse = basicLoginService.basicLoginResponse(basicLoginRequestDto);
        ResultDto<GeneratedTokenDto> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());
        result.setData((GeneratedTokenDto) commonResponse.getData());

        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }

    @GetMapping("/login")
    public ResponseEntity<ResultDto<GeneratedTokenDto>> oauth2Login(@RequestParam("email") String email) {
        CommonResponse<Object> commonResponse = oAuth2LoginService.oAuth2LoginResponse(email);
        ResultDto<GeneratedTokenDto> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());
        result.setData((GeneratedTokenDto) commonResponse.getData());
        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }
}
