package com.example.auth_spring.web.controller.auth.signup;

import com.example.auth_spring.service.auth.signup.SignupService;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.ResultDto;
import com.example.auth_spring.web.dto.auth.signup.SignupRequestDto;
import com.example.auth_spring.web.dto.auth.signup.SignupResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class SignupController {

    private final SignupService signupService;

    @PostMapping("/signup")
    public ResponseEntity<ResultDto<SignupResponseDto>> signup(@RequestBody SignupRequestDto signupRequestDto) {
        CommonResponse<Object> commonResponse = signupService.signupResponse(signupRequestDto);
        ResultDto<SignupResponseDto> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());

        result.setData((SignupResponseDto) commonResponse.getData());
        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }
}
