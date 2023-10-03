package com.example.auth_spring.web.controller.all.signup.exist;

import com.example.auth_spring.service.all.signup.exist.SignupExistService;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.ResultDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/all")
@Api(tags = "Auth APIs")
public class SignupExistController {

    private final SignupExistService signupExistService;

    @ApiOperation(value = "닉네임 중복 확인 API")
    @PostMapping("/signup/exist/nickname/{nickname}")
    public ResponseEntity<ResultDto<Void>> nicknameExist(@ApiParam(name = "nickname", value = "닉네임", example = "nickname")
                                                             @PathVariable String nickname) {
        CommonResponse<Object> commonResponse = signupExistService.nicknameExist(nickname);
        ResultDto<Void> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());

        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }

    @ApiOperation(value = "이메일 중복 확인 API")
    @PostMapping("/signup/exist/email/{email}")
    public ResponseEntity<ResultDto<Void>> emailExist(@ApiParam(name = "email", value = "이메일", example = "abcd@naver.com")
                                                          @PathVariable String email) {
        CommonResponse<Object> commonResponse = signupExistService.emailExist(email);
        ResultDto<Void> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());

        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }

    @ApiOperation(value = "핸드폰 번호 중복 확인 API")
    @PostMapping("/signup/exist/phoneNumber/{phoneNumber}")
    public ResponseEntity<ResultDto<Void>> phoneNumber(@ApiParam(name = "phoneNumber", value = "핸드폰 번호", example = "01000000000")
                                                           @PathVariable String phoneNumber) {
        CommonResponse<Object> commonResponse = signupExistService.phoneNumberExist(phoneNumber);
        ResultDto<Void> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());

        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }
}
