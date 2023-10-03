package com.example.auth_spring.web.controller.user.cart.inquiry;

import com.example.auth_spring.service.user.cart.inquiry.CartInquiryService;
import com.example.auth_spring.web.dto.cart.CartListResponseDto;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.ResultDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
@Api(tags = "Cart APIs")
public class CartInquiryController {

    private final CartInquiryService cartInquiryService;

    @ApiOperation(value = "장바구니 상품 조회 API")
    @GetMapping("/cart/getList")
    public ResponseEntity<ResultDto<CartListResponseDto>> cartList(@ApiIgnore @RequestHeader("Authorization") String bearerAccessToken,
                                                    @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                                    @RequestParam(value = "size", defaultValue = "10", required = false) int size,
                                                    @RequestParam(value = "sortBy", defaultValue = "modifiedAt", required = false) String sortBy) {

        CommonResponse<Object> commonResponse = cartInquiryService.cartInquiryResponse(bearerAccessToken, page, size, sortBy);
        ResultDto<CartListResponseDto> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());
        result.setData((CartListResponseDto) commonResponse.getData());

        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }
}
