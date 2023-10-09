package com.example.auth_spring.web.controller.all.inquiry;

import com.example.auth_spring.service.all.inquiry.CategoryProductInquiryService;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.ResultDto;
import com.example.auth_spring.web.dto.product.ProductListResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/all")
@Api(tags = "Inquiry APIs")
public class CategoryProductInquiryController {

    private final CategoryProductInquiryService categoryProductInquiryService;

    @ApiOperation(value = "카테고리 별 상품 조회 API")
    @GetMapping("/product/category/{categoryName}")
    public ResponseEntity<ResultDto<ProductListResponseDto>> getCategoryProductList(@ApiParam(name = "categoryName", value = "카테고리명", example = "의류") @PathVariable String categoryName,
                                                                                    @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                                                                    @RequestParam(value = "size", defaultValue = "10", required = false) int size,
                                                                                    @RequestParam(value = "sortBy", defaultValue = "modifiedAt", required = false) String sortBy) {
        CommonResponse<Object> commonResponse = categoryProductInquiryService.categoryProductInquiryResponse(categoryName, page, size, sortBy);
        ResultDto<ProductListResponseDto> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());
        result.setData((ProductListResponseDto) commonResponse.getData());

        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);

    }

}
