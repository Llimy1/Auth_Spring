package com.example.auth_spring.service.all.inquiry;

import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.service.user.token.TokenService;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.domain.option.OptionRepository;
import com.example.auth_spring.web.domain.productoption.ProductOption;
import com.example.auth_spring.web.domain.productoption.ProductOptionRepository;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.option.OptionListResponseDto;
import com.example.auth_spring.web.dto.option.OptionResponseDto;
import com.example.auth_spring.web.dto.product.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OptionInquiryService {

    private final CommonService commonService;
    private final OptionRepository optionRepository;


    // 옵션 조회
    public OptionListResponseDto optionInquiry(String productName) {

        List<OptionResponseDto> optionResponseDtoList = optionRepository.findOptionListByProductName(productName);

        return OptionListResponseDto.builder()
                .optionList(optionResponseDtoList)
                .build();
    }

    // API 반환
    public CommonResponse<Object> optionInquiryResponse(String productName) {

        return commonService.successResponse(SuccessCode.OPTION_LIST_INQUIRY_SUCCESS.getDescription(), HttpStatus.OK, optionInquiry(productName));
    }
}

