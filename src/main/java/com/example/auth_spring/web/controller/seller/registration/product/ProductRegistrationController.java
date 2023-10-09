package com.example.auth_spring.web.controller.seller.registration.product;

import com.example.auth_spring.service.seller.registration.product.ProductRegistrationService;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.ResultDto;
import com.example.auth_spring.web.dto.product.ProductIdResponseDto;
import com.example.auth_spring.web.dto.product.ProductRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/seller")
@Api(tags = "Seller APIs")
public class ProductRegistrationController {

    private final ProductRegistrationService productRegistrationService;


    @ApiOperation(value = "판매자 상품 등록 API")
    @PostMapping("/product/{subCategoryName}")
    public ResponseEntity<ResultDto<ProductIdResponseDto>> productRegistration(@ApiIgnore @RequestHeader("Authorization") String bearerAccessToken,
                                                                               @PathVariable String subCategoryName,
                                                                               @RequestBody ProductRequestDto productRequestDto) {
        CommonResponse<Object> commonResponse = productRegistrationService.registrationResponse(bearerAccessToken, subCategoryName, productRequestDto);
        ResultDto<ProductIdResponseDto> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());
        result.setData((ProductIdResponseDto) commonResponse.getData());

        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }
}
