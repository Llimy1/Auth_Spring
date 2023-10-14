package com.example.auth_spring.web.controller.user.cart.registration;

import com.example.auth_spring.service.user.cart.registration.CartRegistrationService;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.ResultDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
@Api(tags = "Cart APIs")
public class CartRegistrationController {

    private final CartRegistrationService cartRegistrationService;

    @ApiOperation(value = "장바구니 상품 등록 API")
    @PostMapping("/cart/{productCount}")
    public ResponseEntity<ResultDto<Void>> cartRegistration(@ApiIgnore @RequestHeader("Authorization") String bearerAccessToken,
                                                            @ApiParam(name = "productName", value = "상품 이름", example = "나이키 맨투맨") @RequestParam("productName") String productName,
                                                            @ApiParam(name = "productCount", value = "상품 개수", example = "1") @PathVariable(value = "productCount") Integer productCount) {

        CommonResponse<Object> commonResponse = cartRegistrationService.cartRegistrationResponse(bearerAccessToken, productName, productCount);
        ResultDto<Void> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());

        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }
}
