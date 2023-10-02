package com.example.auth_spring.web.controller.category.update;


import com.example.auth_spring.service.category.update.CategoryUpdateService;
import com.example.auth_spring.web.dto.category.CategoryUpdateResponseDto;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.ResultDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin")
@Api(tags = "Admin APIs")
public class CategoryUpdateController {

    private final CategoryUpdateService categoryUpdateService;

    @ApiOperation(value = "카테고리명 변경 API")
    @PatchMapping("/category/{beforeCategoryName}/{afterCategoryName}")
    public ResponseEntity<ResultDto<CategoryUpdateResponseDto>> categoryUpdate(@ApiIgnore @RequestHeader("Authorization") String bearerAccessToken,
                                                                               @ApiParam(name = "beforeCategoryName", value = "이전 카테고리명", example = "의류") @PathVariable String beforeCategoryName,
                                                                               @ApiParam(name = "afterCategoryName", value = "바꿀 카테고리명", example = "잡화")@PathVariable String afterCategoryName) {

        CommonResponse<Object> commonResponse = categoryUpdateService.categoryUpdateResponse(bearerAccessToken, beforeCategoryName, afterCategoryName);
        ResultDto<CategoryUpdateResponseDto> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());
        result.setData((CategoryUpdateResponseDto) commonResponse.getData());

        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }
}
