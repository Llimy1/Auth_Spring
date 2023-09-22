package com.example.auth_spring.web.controller.auth.login;

import com.example.auth_spring.security.jwt.dto.GeneratedTokenDto;
import com.example.auth_spring.service.auth.login.BasicLoginService;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.ResultDto;
import com.example.auth_spring.web.dto.auth.login.LoginReqeustDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class LoginController {

    private final BasicLoginService basicLoginService;

    @PostMapping("/login")
    public ResponseEntity<ResultDto<GeneratedTokenDto>> basicLogin(@RequestBody LoginReqeustDto loginReqeustDto) {
        CommonResponse<Object> commonResponse = basicLoginService.basicLoginResponse(loginReqeustDto);
        ResultDto<GeneratedTokenDto> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());
        result.setData((GeneratedTokenDto) commonResponse.getData());

        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }
}
