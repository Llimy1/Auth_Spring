package com.example.auth_spring.web.controller.subcategory.update;


import com.example.auth_spring.service.subcategory.update.SubCategoryUpdateService;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.ResultDto;
import com.example.auth_spring.web.dto.subcategory.SubCategoryRequestDto;
import com.example.auth_spring.web.dto.subcategory.SubCategoryUpdateResponseDto;
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
public class SubCategoryUpdateController {

    private final SubCategoryUpdateService subCategoryUpdateService;

    @ApiOperation(value = "서브 카테고리 변경 API")
    @PatchMapping("/subCategory/{beforeSubCategoryName}/{afterSubCategoryName}")
    public ResponseEntity<ResultDto<SubCategoryUpdateResponseDto>> subCategoryUpdate(@RequestHeader("Authorization") String bearerAccessToken,
                                                                                     @ApiParam(name = "beforeSubCategoryName", value = "이전 서브 카테고리명", example = "맨투맨") @PathVariable String beforeSubCategoryName,
                                                                                     @ApiParam(name = "afterSubCategoryName", value = "바꿀 서브 카테고리명", example = "후드티") @PathVariable String afterSubCategoryName) {
        CommonResponse<Object> commonResponse = subCategoryUpdateService.subCategoryUpdateResponse(bearerAccessToken, beforeSubCategoryName, afterSubCategoryName);
        ResultDto<SubCategoryUpdateResponseDto> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());
        result.setData((SubCategoryUpdateResponseDto) commonResponse.getData());

        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }
}
