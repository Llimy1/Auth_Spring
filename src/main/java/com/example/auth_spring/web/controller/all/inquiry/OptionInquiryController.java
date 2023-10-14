package com.example.auth_spring.web.controller.all.inquiry;

import com.example.auth_spring.service.all.inquiry.OptionInquiryService;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.ResultDto;
import com.example.auth_spring.web.dto.option.OptionListResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/all")
@Api(tags = "Option APIs")
public class OptionInquiryController {

    private final OptionInquiryService optionInquiryService;

    @ApiOperation(value = "옵션 조회 API")
    @GetMapping("/option")
    public ResponseEntity<ResultDto<OptionListResponseDto>> optionList(@ApiParam(name = "productName", value = "productName", example = "나이키 맨투맨") @RequestParam("productName") String productName) {

        CommonResponse<Object> commonResponse = optionInquiryService.optionInquiryResponse(productName);
        ResultDto<OptionListResponseDto> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());
        result.setData((OptionListResponseDto) commonResponse.getData());

        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }

}
