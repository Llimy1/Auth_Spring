package com.example.auth_spring.web.controller.seller.inquiry.brand;

import com.example.auth_spring.service.seller.inquiry.brand.BrandInquiryService;
import com.example.auth_spring.web.dto.brand.BrandListResponseDto;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.ResultDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/seller")
@Api(tags = "Brand APIs")
public class BrandInquiryController {

    private final BrandInquiryService brandInquiryService;


    @ApiOperation(value = "브랜드 조회 API")
    @GetMapping("/brand")
    public ResponseEntity<ResultDto<BrandListResponseDto>> brandList(@ApiIgnore @RequestHeader("Authorization") String bearerAccessToken) {

        CommonResponse<Object> commonResponse = brandInquiryService.brandInquiryResponse(bearerAccessToken);
        ResultDto<BrandListResponseDto> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());
        result.setData((BrandListResponseDto) commonResponse.getData());

        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }

}
