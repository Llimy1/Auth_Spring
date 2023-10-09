package com.example.auth_spring.service.seller.inquiry.option;

import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.service.user.token.TokenService;
import com.example.auth_spring.web.domain.productoption.ProductOptionRepository;
import com.example.auth_spring.web.dto.option.OptionListResponseDto;
import com.example.auth_spring.web.dto.option.OptionResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class OptionInquiryServiceTest {

    private OptionInquiryService optionInquiryService;
    private ProductOptionRepository productOptionRepository;
    private TokenService tokenService;
    private CommonService commonService;

    @BeforeEach
    void setup() {
        tokenService = mock(TokenService.class);
        productOptionRepository = mock(ProductOptionRepository.class);
        optionInquiryService = new OptionInquiryService(tokenService, commonService, productOptionRepository);
    }

    @Test
    @DisplayName("[Service] 옵션 조회 성공")
    void optionInquirySuccess() {

        String bearerAccessToken = "Bearer accessToken";
        String email = "email";

        List<OptionResponseDto> optionResponseDtoList = List.of(OptionResponseDto.builder()
                .optionName("XL")
                .build());

        //given
        given(tokenService.accessTokenEmail(anyString()))
                .willReturn(email);
        given(productOptionRepository.findOptionListByUserEmail(anyString()))
                .willReturn(optionResponseDtoList);

        //when
        OptionListResponseDto result = optionInquiryService.optionInquiry(bearerAccessToken);

        //then
        assertThat(result.getOptionList().get(0).getOptionName()).isEqualTo(optionResponseDtoList.get(0).getOptionName());
    }
}