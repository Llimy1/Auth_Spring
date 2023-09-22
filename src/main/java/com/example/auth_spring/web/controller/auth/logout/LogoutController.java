package com.example.auth_spring.web.controller.auth.logout;

import com.example.auth_spring.service.auth.logout.BasicLogoutService;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.ResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class LogoutController {

    private final BasicLogoutService basicLogoutService;

    @DeleteMapping("/logout")
    public ResponseEntity<ResultDto<Void>> logout(@RequestHeader("Authorization") String accessToken) {
        CommonResponse<Object> commonResponse = basicLogoutService.logoutResponse(accessToken);
        ResultDto<Void> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());

        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }


}
