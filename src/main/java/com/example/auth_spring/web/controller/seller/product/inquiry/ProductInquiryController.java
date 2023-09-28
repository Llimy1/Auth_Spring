package com.example.auth_spring.web.controller.seller.product.inquiry;

import com.example.auth_spring.service.seller.inquiry.ProductInquiryService;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.ResultDto;
import com.example.auth_spring.web.dto.product.ProductListResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@Api(tags = "Seller APIs")
public class ProductInquiryController {

    private final ProductInquiryService productInquiryService;

    @ApiOperation(value = "판매자 상품 조회 API")
    @GetMapping("/seller/productList")
    public ResponseEntity<ResultDto<ProductListResponseDto>> productList(@RequestHeader("Authorization") String bearerAccessToken,
                                                                         @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                                                         @RequestParam(value = "size", defaultValue = "10", required = false) int size,
                                                                         @RequestParam(value = "sortBy", defaultValue = "modifiedAt", required = false) String sortBy) {
        CommonResponse<Object> commonResponse = productInquiryService.productInquiryResponse(bearerAccessToken, page, size, sortBy);
        ResultDto<ProductListResponseDto> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());
        result.setData((ProductListResponseDto) commonResponse.getData());

        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }
}
