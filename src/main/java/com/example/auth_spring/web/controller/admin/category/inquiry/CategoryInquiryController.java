package com.example.auth_spring.web.controller.admin.category.inquiry;

import com.example.auth_spring.service.admin.category.inquiry.CategoryInquiryService;
import com.example.auth_spring.web.dto.category.CategoryListResponseDto;
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
@RequestMapping("/api/v1/admin")
@Api(tags = "Admin APIs")
public class CategoryInquiryController {

    private final CategoryInquiryService categoryInquiryService;

    @ApiOperation(value = "카테고리 전체 조회 API")
    @GetMapping("/category/getList")
    public ResponseEntity<ResultDto<CategoryListResponseDto>> categoryList(@ApiIgnore @RequestHeader("Authorization") String bearerAccessToken) {
        CommonResponse<Object> commonResponse = categoryInquiryService.categoryListResponse(bearerAccessToken);
        ResultDto<CategoryListResponseDto> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());
        result.setData((CategoryListResponseDto) commonResponse.getData());

        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }
}
