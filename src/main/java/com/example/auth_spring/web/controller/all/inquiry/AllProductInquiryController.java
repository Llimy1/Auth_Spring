package com.example.auth_spring.web.controller.all.inquiry;

import com.example.auth_spring.service.all.inquiry.AllProductInquiryService;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.ResultDto;
import com.example.auth_spring.web.dto.product.ProductListResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/all")
@Api(tags = "All APIs")
public class AllProductInquiryController {

    private final AllProductInquiryService allProductInquiryService;

    @ApiOperation(value = "전체 상품 조회 API")
    @GetMapping("/product/getAllList")
    public ResponseEntity<ResultDto<ProductListResponseDto>> getAllList(@RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                                                        @RequestParam(value = "size", defaultValue = "10", required = false) int size,
                                                                        @RequestParam(value = "sortBy", defaultValue = "modifiedAt", required = false) String sortBy) {

        CommonResponse<Object> commonResponse = allProductInquiryService.allProductInquiryResponse(page, size, sortBy);
        ResultDto<ProductListResponseDto> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());
        result.setData((ProductListResponseDto) commonResponse.getData());

        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }
}
