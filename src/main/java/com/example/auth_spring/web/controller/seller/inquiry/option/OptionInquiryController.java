package com.example.auth_spring.web.controller.seller.inquiry.option;

import com.example.auth_spring.service.seller.inquiry.option.OptionInquiryService;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.ResultDto;
import com.example.auth_spring.web.dto.option.OptionListResponseDto;
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
@RequestMapping("/api/v1/seller")
@Api(tags = "Option APIs")
public class OptionInquiryController {

    private final OptionInquiryService optionInquiryService;

    @ApiOperation(value = "옵션 조회 API")
    @GetMapping("/option")
    public ResponseEntity<ResultDto<OptionListResponseDto>> optionList(@ApiIgnore @RequestHeader("Authorization") String bearerAccessToken) {

        CommonResponse<Object> commonResponse = optionInquiryService.optionInquiryResponse(bearerAccessToken);
        ResultDto<OptionListResponseDto> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());
        result.setData((OptionListResponseDto) commonResponse.getData());

        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }

}
