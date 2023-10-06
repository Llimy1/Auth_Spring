package com.example.auth_spring.web.controller.seller.product.inquiry.brand;

import com.example.auth_spring.security.jwt.service.JwtProvider;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.service.seller.inquiry.brand.BrandInquiryService;
import com.example.auth_spring.service.seller.inquiry.product.ProductInquiryService;
import com.example.auth_spring.service.user.token.TokenService;
import com.example.auth_spring.type.ResponseStatus;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.domain.brand.Brand;
import com.example.auth_spring.web.domain.user.User;
import com.example.auth_spring.web.dto.brand.BrandListResponseDto;
import com.example.auth_spring.web.dto.brand.BrandResponseDto;
import com.example.auth_spring.web.dto.common.CommonResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BrandInquiryController.class)
class BrandInquiryControllerTest {

    @MockBean
    private BrandInquiryService brandInquiryService;

    @MockBean
    private CommonService commonService;

    @MockBean
    private JwtProvider jwtProvider;

    @MockBean
    private TokenService tokenService;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mvc;

    private Brand brand;
    private User user;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("[API] 브랜드 조회 성공")
    @WithMockUser(roles = "SELLER")
    void brandInquirySuccess() throws Exception {

        String bearerAccessToken = "Bearer accessToken";

        user = new User();
        ReflectionTestUtils.setField(user, "id", 1L);

        brand = Brand.builder()
                .user(user)
                .name("나이키")
                .build();

        BrandResponseDto brandResponseDto = BrandResponseDto.builder()
                .brandName("나이키")
                .build();

        List<BrandResponseDto> brandList = Collections.singletonList(brandResponseDto);

        BrandListResponseDto brandListResponseDto = BrandListResponseDto.builder()
                .brandList(brandList)
                .build();

        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .httpStatus(HttpStatus.OK)
                .status(ResponseStatus.SUCCESS.getDescription())
                .message(SuccessCode.BRAND_ALL_INQUIRY_SUCCESS.getDescription())
                .data(brandListResponseDto)
                .build();

        //given
        given(brandInquiryService.brandInquiryResponse(anyString()))
                .willReturn(commonResponse);

        //when
        //then
        mvc.perform(get("/api/v1/seller/brand/getList")
                        .with(csrf())
                        .header("Authorization", bearerAccessToken)
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ResponseStatus.SUCCESS.getDescription()))
                .andExpect(jsonPath("$.message").value(SuccessCode.BRAND_ALL_INQUIRY_SUCCESS.getDescription()))
                .andExpect(jsonPath("$.data.brandList[0].brandName").value("나이키"))
                .andDo(print());
    }

}