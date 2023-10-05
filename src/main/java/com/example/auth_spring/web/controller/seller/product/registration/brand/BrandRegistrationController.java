package com.example.auth_spring.web.controller.seller.product.registration.brand;

import com.example.auth_spring.service.seller.registration.brand.BrandRegistrationService;
import com.example.auth_spring.web.dto.brand.BrandRequestDto;
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
@RequestMapping("/api/v1/seller")
@Api(tags = "Brand APIs")
public class BrandRegistrationController {

    private final BrandRegistrationService brandRegistrationService;

    @ApiOperation(value = "브랜드 저장 API")
    @PostMapping("/brand/registration")
    public ResponseEntity<ResultDto<Void>> brandRegistration(@ApiIgnore @RequestHeader("Authorization") String bearerAccessToken,
                                                             @RequestBody BrandRequestDto brandRequestDto) {
        CommonResponse<Object> commonResponse = brandRegistrationService.brandRegistrationResponse(bearerAccessToken, brandRequestDto);
        ResultDto<Void> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());

        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }

}
