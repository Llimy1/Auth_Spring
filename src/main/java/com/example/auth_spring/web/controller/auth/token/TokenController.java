package com.example.auth_spring.web.controller.auth.token;

import com.example.auth_spring.security.jwt.dto.GeneratedTokenDto;
import com.example.auth_spring.security.jwt.service.TokenService;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.ResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/reissue")
    public ResponseEntity<ResultDto<GeneratedTokenDto>> reissue(@RequestHeader("Refresh-Token") String bearerRefreshToken) {
        CommonResponse<Object> commonResponse = tokenService.reissueResponse(bearerRefreshToken);
        ResultDto<GeneratedTokenDto> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());
        result.setData((GeneratedTokenDto) commonResponse.getData());

        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }


}
