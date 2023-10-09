package com.example.auth_spring.web.controller.all.inquiry;

import com.example.auth_spring.service.all.inquiry.SubCategoryProductInquiryService;
import com.example.auth_spring.web.domain.subcategory.SubCategory;
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
public class SubCategoryProductInquiryController {

    private final SubCategoryProductInquiryService subCategoryProductInquiryService;

    @ApiOperation(value = "서브 카테고리 별 상품 조회 API")
    @GetMapping("/product/subCategory/{subCategoryName}")
    public ResponseEntity<ResultDto<ProductListResponseDto>> getSubCategoryProductList(@ApiParam(name = "subCategoryName", value = "서브 카테고리명", example = "맨투맨")@PathVariable String subCategoryName,
                                                                                       @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                                                                       @RequestParam(value = "size", defaultValue = "10", required = false) int size,
                                                                                       @RequestParam(value = "sortBy", defaultValue = "modifiedAt", required = false) String sortBy) {
        CommonResponse<Object> commonResponse = subCategoryProductInquiryService.subCategoryProductInquiryResponse(subCategoryName, page, size, sortBy);
        ResultDto<ProductListResponseDto> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());
        result.setData((ProductListResponseDto) commonResponse.getData());

        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }


}
