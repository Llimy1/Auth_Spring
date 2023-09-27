package com.example.auth_spring.web.controller.auth.login;

import com.example.auth_spring.service.auth.login.BasicLoginService;
import com.example.auth_spring.service.auth.login.OAuth2LoginService;
import com.example.auth_spring.web.dto.auth.login.BasicLoginRequestDto;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.ResultDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@Api(tags = "Auth APIs")
public class LoginController {

    private final BasicLoginService basicLoginService;
    private final OAuth2LoginService oAuth2LoginService;

    @ApiOperation(value = "자체 로그인 API", notes = "자체 로그인 진행")
    @PostMapping("/all/login/basic")
    public ResponseEntity<ResultDto<Void>> basicLogin(@RequestBody BasicLoginRequestDto basicLoginRequestDto, HttpServletResponse httpServletResponse) {
        CommonResponse<Object> commonResponse = basicLoginService.basicLoginResponse(basicLoginRequestDto, httpServletResponse);
        ResultDto<Void> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());

        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }

//    @ApiOperation(value = "소셜 회원 가입 API", notes = "소셜 로그인 후 회원 정보 없을 시 회원 가입 진행")
//    @PostMapping("/login/oath2")
//    public ResponseEntity<ResultDto<Void>> oauth2Login(HttpServletResponse httpServletResponse) {
//        CommonResponse<Object> commonResponse = oAuth2LoginService.oAuth2LoginResponse(httpServletResponse);
//        ResultDto<Void> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());
//
//        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
//    }
}
