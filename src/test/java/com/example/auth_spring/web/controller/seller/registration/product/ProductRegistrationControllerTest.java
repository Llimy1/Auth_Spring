package com.example.auth_spring.web.controller.seller.registration.product;

import com.example.auth_spring.security.jwt.service.JwtProvider;
import com.example.auth_spring.service.user.token.TokenService;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.service.seller.registration.product.ProductRegistrationService;
import com.example.auth_spring.type.ErrorCode;
import com.example.auth_spring.type.ResponseStatus;
import com.example.auth_spring.type.Role;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.product.ProductIdResponseDto;
import com.example.auth_spring.web.dto.product.ProductRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductRegistrationController.class)
class ProductRegistrationControllerTest {

    @MockBean
    private ProductRegistrationService productRegistrationService;

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

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    private ProductRequestDto productRequestDto() {
        return ProductRequestDto.builder()
                .productName("옷")
                .productPrice(10000L)
                .deliveryPrice(3000)
                .brandName("나이키")
                .discountRate(35)
                .isDiscount(true)
                .build();
    }

    @Test
    @DisplayName("[API] 상품 등록 성공")
    @WithMockUser(roles = "SELLER")
    void productRegistrationSuccess() throws Exception {

        String body = objectMapper.writeValueAsString(productRequestDto());

        String bearerAccessToken = "Bearer accessToken";

        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .httpStatus(HttpStatus.CREATED)
                .status(ResponseStatus.SUCCESS.getDescription())
                .message(SuccessCode.PRODUCT_REGISTRATION_SUCCESS.getDescription())
                .data(new ProductIdResponseDto(1L))
                .build();

        //given
        given(productRegistrationService.registrationResponse(any(), any(), any()))
                .willReturn(commonResponse);


        //when
        //then
        mvc.perform(post("/api/v1/seller/product/{subCategoryName}", "서브 카테고리명")
                        .with(csrf())
                        .header("Authorization", bearerAccessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(ResponseStatus.SUCCESS.getDescription()))
                .andExpect(jsonPath("$.message").value(SuccessCode.PRODUCT_REGISTRATION_SUCCESS.getDescription()))
                .andExpect(jsonPath("$.data.productId").value(1L))
                .andDo(print());
    }

    @Test
    @DisplayName("[API] 상품 등록 실패 - 판매자 권한 없음")
    @WithMockUser(roles = "USER")
    void productRegistrationFail() throws Exception {
        String body = objectMapper.writeValueAsString(productRequestDto());

        String bearerAccessToken = "Bearer accessToken";

        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .status(ResponseStatus.FAIL.getDescription())
                .message(ErrorCode.AUTHORITY_NOT_SELLER.getDescription())
                .data(null)
                .build();

        //given
        given(tokenService.findUserRole(anyString())).willReturn(Role.USER.getKey());

        given(productRegistrationService.registrationResponse(any(), any(), any()))
                .willReturn(commonResponse);


        //when
        //then
        mvc.perform(post("/api/v1/seller/product/{subCategoryName}", "서브 카테고리명")
                        .with(csrf())
                        .header("Authorization", bearerAccessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(ResponseStatus.FAIL.getDescription()))
                .andExpect(jsonPath("$.message").value(ErrorCode.AUTHORITY_NOT_SELLER.getDescription()))
                .andDo(print());
    }
}