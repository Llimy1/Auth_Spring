package com.example.auth_spring.web.controller.auth.token;

import com.example.auth_spring.security.jwt.dto.GeneratedTokenDto;
import com.example.auth_spring.security.jwt.service.TokenService;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.ResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/reissue")
    public ResponseEntity<ResultDto<GeneratedTokenDto>> reissue(@RequestHeader("REFRESH-TOKEN") String bearerRefreshToken, HttpServletResponse httpServletResponse) {
        CommonResponse<Object> commonResponse = tokenService.reissueResponse(bearerRefreshToken, httpServletResponse);
        ResultDto<GeneratedTokenDto> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());

        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }


}
