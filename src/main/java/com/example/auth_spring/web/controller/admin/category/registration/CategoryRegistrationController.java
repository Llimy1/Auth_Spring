package com.example.auth_spring.web.controller.admin.category.registration;

import com.example.auth_spring.service.admin.category.registration.CategoryRegistrationService;
import com.example.auth_spring.web.dto.category.CategoryRequestDto;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.ResultDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin")
@Api(tags = "Admin APIs")
public class CategoryRegistrationController {

    private final CategoryRegistrationService categoryRegistrationService;


    @ApiOperation(value = "카테고리 등록 API")
    @PostMapping("/category")
    public ResponseEntity<ResultDto<Void>> categoryRegistration(@ApiIgnore @RequestHeader("Authorization") String bearAccessToken,
                                                                @RequestBody CategoryRequestDto categoryRequestDto) {
        CommonResponse<Object> commonResponse = categoryRegistrationService.categoryRegistrationResponse(bearAccessToken, categoryRequestDto);
        ResultDto<Void> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());

        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }
}
