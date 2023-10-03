package com.example.auth_spring.web.controller.user.logout;

import com.example.auth_spring.service.user.logout.BasicLogoutService;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.ResultDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
@Api(tags = "Auth APIs")
public class LogoutController {

    private final BasicLogoutService basicLogoutService;

    @ApiOperation(value = "로그아웃 API")
    @DeleteMapping("/logout")
    public ResponseEntity<ResultDto<Void>> logout(@ApiIgnore @RequestHeader("Authorization") String accessToken) {
        CommonResponse<Object> commonResponse = basicLogoutService.logoutResponse(accessToken);
        ResultDto<Void> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());

        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }


}
