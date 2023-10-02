package com.example.auth_spring.web.controller.auth.signup;

import com.example.auth_spring.service.auth.signup.OAuth2SignupService;
import com.example.auth_spring.service.auth.signup.BasicSignupService;
import com.example.auth_spring.web.dto.auth.signup.OAuth2SignupRequestDto;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.ResultDto;
import com.example.auth_spring.web.dto.auth.signup.BasicSignupRequestDto;
import com.example.auth_spring.web.dto.auth.signup.UserIdResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/all")
@Api(tags = "Auth APIs")
public class SignupController {

    private final BasicSignupService basicSignupService;
    private final OAuth2SignupService oAuth2SignupService;

    @ApiOperation(value = "자체 회원가입 API", notes = "자체 회원가입")
    @PostMapping("/signup/basic")
    public ResponseEntity<ResultDto<UserIdResponseDto>> signup(@RequestBody BasicSignupRequestDto basicSignupRequestDto) {
        CommonResponse<Object> commonResponse = basicSignupService.signupResponse(basicSignupRequestDto);
        ResultDto<UserIdResponseDto> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());

        result.setData((UserIdResponseDto) commonResponse.getData());
        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }

    @ApiOperation(value = "소셜 회원가입 API", notes = "소셜 회원가입")
    @PostMapping("/signup/oauth2")
    public ResponseEntity<ResultDto<UserIdResponseDto>> oauth2Signup(@ApiParam(name = "email", value = "email", example = "abcd@naver.com") @RequestParam String email,
                                                                     @RequestBody OAuth2SignupRequestDto oAuth2SignupRequestDto) {
        CommonResponse<Object> commonResponse = oAuth2SignupService.oauth2SignupResponse(email, oAuth2SignupRequestDto);
        ResultDto<UserIdResponseDto> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());

        result.setData((UserIdResponseDto) commonResponse.getData());
        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }
}
