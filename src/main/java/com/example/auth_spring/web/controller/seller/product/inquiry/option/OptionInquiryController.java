package com.example.auth_spring.web.controller.seller.product.inquiry.option;

import com.example.auth_spring.service.seller.inquiry.option.OptionInquiryService;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.ResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/seller")
public class OptionInquiryController {

    private final OptionInquiryService optionInquiryService;

    @GetMapping("/option/getList")
    public ResponseEntity<ResultDto<Void>> optionList(@RequestHeader("Authorization") String bearerAccessToken) {

        CommonResponse<Object> commonResponse = optionInquiryService.optionInquiryResponse(bearerAccessToken);

        return null;
    }

}
