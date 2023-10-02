package com.example.auth_spring.web.controller.subcategory.delete;

import com.example.auth_spring.service.subcategory.delete.SubCategoryDeleteService;
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
public class SubCategoryDeleteController {

    private final SubCategoryDeleteService subCategoryDeleteService;

    @ApiOperation(value = "서브 카테고리 삭제 API")
    @DeleteMapping("/subCategory/delete/{subCategoryName}")
    public ResponseEntity<ResultDto<Void>> subCategoryDelete(@ApiIgnore @RequestHeader("Authorization") String bearerAccessToken,
                                                             @ApiParam(name = "subCategoryName", value = "서브 카테고리명", example = "맨투맨") @PathVariable String subCategoryName) {

        CommonResponse<Object> commonResponse = subCategoryDeleteService.subCategoryDeleteResponse(bearerAccessToken, subCategoryName);
        ResultDto<Void> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());

        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }
}
