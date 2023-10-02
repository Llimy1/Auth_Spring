package com.example.auth_spring.web.controller.seller;

import com.example.auth_spring.service.seller.SellerService;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.ResultDto;
import com.example.auth_spring.web.dto.auth.signup.UserIdResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/seller")
@Api(tags = "Seller APIs")
public class SellerController {

    private final SellerService sellerService;

    @ApiOperation(value = "판매자 전환 API")
    @PostMapping("/conversion")
    public ResponseEntity<ResultDto<UserIdResponseDto>> sellerConversion(@ApiIgnore @RequestHeader("Authorization") String bearerAccessToken) {

        CommonResponse<Object> commonResponse = sellerService.conversionResponse(bearerAccessToken);
        ResultDto<UserIdResponseDto> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());
        result.setData((UserIdResponseDto) commonResponse.getData());

        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }
}
