package com.example.auth_spring.service.seller.inquiry.brand;

import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.service.user.token.TokenService;
import com.example.auth_spring.web.domain.brand.Brand;
import com.example.auth_spring.web.domain.brand.BrandRepository;
import com.example.auth_spring.web.domain.user.User;
import com.example.auth_spring.web.dto.brand.BrandListResponseDto;
import com.example.auth_spring.web.dto.brand.BrandResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class BrandInquiryServiceTest {

    private BrandInquiryService brandInquiryService;
    private BrandRepository brandRepository;
    private TokenService tokenService;
    private CommonService commonService;

    private Brand brand;
    private User user;

    @BeforeEach
    void setup() {
        tokenService = mock(TokenService.class);
        brandRepository = mock(BrandRepository.class);
        brandInquiryService = new BrandInquiryService(tokenService, commonService, brandRepository);

        user = new User();
        ReflectionTestUtils.setField(user, "id", 1L);

        brand = Brand.builder()
                .user(user)
                .name("나이키")
                .build();
    }

    @Test
    @DisplayName("[Service] 브랜드 조회 성공")
    void brandInquirySuccess() {

        String bearerAccessToken = "Bearer accessToken";
        String email = "email";

        BrandResponseDto brandResponseDto = BrandResponseDto.builder()
                .brandName("나이키")
                .build();

        List<BrandResponseDto> brandList = Collections.singletonList(brandResponseDto);


        //given
        given(tokenService.accessTokenEmail(anyString()))
                .willReturn(email);
        given(brandRepository.findBrandListByUserEmail(anyString()))
                .willReturn(brandList);


        //when
        BrandListResponseDto result = brandInquiryService.brandInquiry(bearerAccessToken);

        //then
        assertThat(result.getBrandList().get(0).getBrandName()).isEqualTo(brandList.get(0).getBrandName());

    }






}