package com.example.auth_spring.service.seller.inquiry.option;

import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.service.user.token.TokenService;
import com.example.auth_spring.web.domain.productoption.ProductOptionRepository;
import com.example.auth_spring.web.dto.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OptionInquiryService {

    private final TokenService tokenService;
    private final CommonService commonService;
    private final ProductOptionRepository productOptionRepository;

    public void optionInquiry(String bearerAccessToken) {



    }

    public CommonResponse<Object> optionInquiryResponse(String bearerAccessToken) {

        return null;
    }
}
