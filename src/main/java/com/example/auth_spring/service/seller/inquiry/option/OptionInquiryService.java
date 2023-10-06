package com.example.auth_spring.service.seller.inquiry.option;

import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.service.user.token.TokenService;
import com.example.auth_spring.type.SuccessCode;
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

    private final TokenService tokenService;
    private final CommonService commonService;
    private final ProductOptionRepository productOptionRepository;


    // 옵션 조회
    public OptionListResponseDto optionInquiry(String bearerAccessToken) {
        tokenService.accessTokenExpiration(bearerAccessToken);

        String email = tokenService.accessTokenEmail(bearerAccessToken);

        List<OptionResponseDto> optionResponseDtoList = productOptionRepository.findOptionListByUserEmail(email);

        return OptionListResponseDto.builder()
                .optionList(optionResponseDtoList)
                .build();

    }

    // API 반환
    public CommonResponse<Object> optionInquiryResponse(String bearerAccessToken) {

        return commonService.successResponse(SuccessCode.OPTION_LIST_INQUIRY_SUCCESS.getDescription(), HttpStatus.OK, optionInquiry(bearerAccessToken));
    }
}
