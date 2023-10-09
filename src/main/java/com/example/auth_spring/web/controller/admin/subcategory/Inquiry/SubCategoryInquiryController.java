package com.example.auth_spring.web.controller.admin.subcategory.Inquiry;

import com.example.auth_spring.service.admin.subcategory.Inquiry.SubCategoryInquiryService;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.ResultDto;
import com.example.auth_spring.web.dto.subcategory.SubCategoryListResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin")
@Api(tags = "Admin APIs")
public class SubCategoryInquiryController {

    private final SubCategoryInquiryService subCategoryInquiryService;

    @ApiOperation(value = "서브 카테고리 조회")
    @GetMapping("/subCategory/{categoryName}")
    public ResponseEntity<ResultDto<SubCategoryListResponseDto>> subCategoryList(@RequestHeader("Authorization") String bearerAccessToken,
                                                                                 @ApiParam(name = "categoryName", value = "카테고리명", example = "의류") @PathVariable String categoryName) {
        CommonResponse<Object> commonResponse = subCategoryInquiryService.subCategoryListResponse(bearerAccessToken, categoryName);
        ResultDto<SubCategoryListResponseDto> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());
        result.setData((SubCategoryListResponseDto) commonResponse.getData());

        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }
}
