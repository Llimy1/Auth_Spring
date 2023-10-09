package com.example.auth_spring.web.controller.admin.subcategory.registration;

import com.example.auth_spring.service.admin.subcategory.registration.SubCategoryRegistrationService;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.ResultDto;
import com.example.auth_spring.web.dto.subcategory.SubCategoryRequestDto;
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
public class SubCategoryRegistrationController {

    private final SubCategoryRegistrationService subCategoryRegistrationService;

    @ApiOperation(value = "서브 카테고리 등록 API")
    @PostMapping("/subCategory/{categoryName}")
    public ResponseEntity<ResultDto<Void>> subCategoryRegistration(@ApiIgnore @RequestHeader("Authorization") String bearerAccessToken,
                                                                   @ApiParam(name = "categoryName", value = "카테고리명", example = "의류") @PathVariable String categoryName,
                                                                   @RequestBody SubCategoryRequestDto subCategoryRequestDto) {

        CommonResponse<Object> commonResponse = subCategoryRegistrationService.subCategoryRegistrationResponse(bearerAccessToken, categoryName, subCategoryRequestDto);
        ResultDto<Void> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());

        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }
}
