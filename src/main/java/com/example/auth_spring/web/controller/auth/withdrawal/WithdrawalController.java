package com.example.auth_spring.web.controller.auth.withdrawal;

import com.example.auth_spring.service.auth.withdrawal.WithdrawalService;
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
public class WithdrawalController {

    private final WithdrawalService withdrawalService;

    @DeleteMapping("/withdrawal")
    public ResponseEntity<ResultDto<Void>> withdrawal(@RequestHeader("Authorization") String accessToken) {
        CommonResponse<Object> commonResponse = withdrawalService.withdrawalResponse(accessToken);
        ResultDto<Void> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());

        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }


}
