package com.example.auth_spring.web.controller.seller.product.registration.option;

import com.example.auth_spring.service.seller.registration.option.OptionRegistrationService;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.ResultDto;
import com.example.auth_spring.web.dto.option.OptionRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/seller")
@Api(tags = "Option APIs")
public class OptionRegistrationController {

    private final OptionRegistrationService optionRegistrationService;

    @ApiOperation(value = "옵션 저장 API")
    @PostMapping("/option/registration")
    public ResponseEntity<ResultDto<Void>> optionRegistration(@ApiIgnore @RequestHeader("Authorization") String bearerAccessToken,
                                                              @RequestBody OptionRequestDto optionRequestDto) {

        CommonResponse<Object> commonResponse = optionRegistrationService.optionRegistrationResponse(bearerAccessToken, optionRequestDto);
        ResultDto<Void> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());

        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }

}

