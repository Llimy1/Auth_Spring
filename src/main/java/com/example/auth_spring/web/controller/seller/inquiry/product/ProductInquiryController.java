package com.example.auth_spring.web.controller.seller.inquiry.product;

import com.example.auth_spring.service.seller.inquiry.product.ProductInquiryService;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.ResultDto;
import com.example.auth_spring.web.dto.product.ProductListResponseDto;
import com.example.auth_spring.web.dto.seller.SellerProductListResponseDto;
import com.example.auth_spring.web.dto.seller.SellerProductResponseDto;
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
public class ProductInquiryController {

    private final ProductInquiryService productInquiryService;

    @ApiOperation(value = "판매자 상품 조회 API")
    @GetMapping("/product")
    public ResponseEntity<ResultDto<SellerProductListResponseDto>> productList(@ApiIgnore @RequestHeader("Authorization") String bearerAccessToken,
                                                                               @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                                                               @RequestParam(value = "size", defaultValue = "10", required = false) int size,
                                                                               @RequestParam(value = "sortBy", defaultValue = "modifiedAt", required = false) String sortBy) {
        CommonResponse<Object> commonResponse = productInquiryService.productInquiryResponse(bearerAccessToken, page, size, sortBy);
        ResultDto<SellerProductListResponseDto> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());
        result.setData((SellerProductListResponseDto) commonResponse.getData());

        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }
}
