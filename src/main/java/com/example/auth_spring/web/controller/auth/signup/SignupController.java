package com.example.auth_spring.web.controller.auth.signup;

import com.example.auth_spring.service.auth.signup.OAuth2SignupService;
import com.example.auth_spring.service.auth.signup.BasicSignupService;
import com.example.auth_spring.web.dto.auth.signup.OAuth2SignupRequestDto;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.ResultDto;
import com.example.auth_spring.web.dto.auth.signup.BasicSignupRequestDto;
import com.example.auth_spring.web.dto.auth.signup.SignupResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class SignupController {

    private final BasicSignupService basicSignupService;
    private final OAuth2SignupService oAuth2SignupService;

    @PostMapping("/signup/basic")
    public ResponseEntity<ResultDto<SignupResponseDto>> signup(@RequestBody BasicSignupRequestDto basicSignupRequestDto) {
        CommonResponse<Object> commonResponse = basicSignupService.signupResponse(basicSignupRequestDto);
        ResultDto<SignupResponseDto> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());

        result.setData((SignupResponseDto) commonResponse.getData());
        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }

    @PostMapping("/signup/oauth2")
    public ResponseEntity<ResultDto<SignupResponseDto>> oauth2Signup(@RequestBody OAuth2SignupRequestDto oAuth2SignupRequestDto) {
        CommonResponse<Object> commonResponse = oAuth2SignupService.oauth2SignupResponse(oAuth2SignupRequestDto);
        ResultDto<SignupResponseDto> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());

        result.setData((SignupResponseDto) commonResponse.getData());
        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }
}
