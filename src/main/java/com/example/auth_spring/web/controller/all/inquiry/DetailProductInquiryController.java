package com.example.auth_spring.web.controller.all.inquiry;

import com.example.auth_spring.service.all.inquiry.DetailProductInquiryService;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.ResultDto;
import com.example.auth_spring.web.dto.product.ProductDetailAndImageListResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/all")
@Api(tags = "Inquiry APIs")
public class DetailProductInquiryController {

    private final DetailProductInquiryService detailProductInquiryService;

    @ApiOperation(value = "상품 상세 정보 조회 API")
    @GetMapping("/product/detail")
    public ResponseEntity<ResultDto<ProductDetailAndImageListResponseDto>> detailProduct(@ApiParam(name = "productName", value = "상품 이름", example = "나이키 맨투맨") @RequestParam(value = "productName") String productName) {

        CommonResponse<Object> commonResponse = detailProductInquiryService.detailProductInquiryResponse(productName);
        ResultDto<ProductDetailAndImageListResponseDto> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());
        result.setData((ProductDetailAndImageListResponseDto) commonResponse.getData());

        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }


}
