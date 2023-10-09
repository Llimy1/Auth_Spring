package com.example.auth_spring.web.controller.all.search;

import com.example.auth_spring.service.all.search.SearchProductService;
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
@Api(tags = "Search APIs")
public class SearchProductController {

    private final SearchProductService searchProductService;

    @ApiOperation(value = "상품 이름으로 검색 API")
    @GetMapping("/search")
    public ResponseEntity<ResultDto<ProductListResponseDto>> searchNameProductList(@ApiParam(name = "keyword", value = "검색 키워드", example = "나이키") @RequestParam(value = "keyword") String keyword,
                                                                               @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                                                               @RequestParam(value = "size", defaultValue = "10", required = false) int size,
                                                                               @RequestParam(value = "sortBy", defaultValue = "modifiedAt", required = false) String sortBy) {
        CommonResponse<Object> commonResponse = searchProductService.searchNameProductListResponse(keyword, page, size, sortBy);
        ResultDto<ProductListResponseDto> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());
        result.setData((ProductListResponseDto) commonResponse.getData());

        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }
}
