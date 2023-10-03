package com.example.auth_spring.web.controller.user.cart.registration;

import com.example.auth_spring.security.jwt.service.JwtProvider;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.service.user.cart.registration.CartRegistrationService;
import com.example.auth_spring.service.user.token.TokenService;
import com.example.auth_spring.type.ErrorCode;
import com.example.auth_spring.type.ResponseStatus;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.dto.common.CommonResponse;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartRegistrationController.class)
class CartRegistrationControllerTest {

    @MockBean
    private CartRegistrationService cartRegistrationService;

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

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("[API] 장바구니 상품 등록 성공")
    @WithMockUser(roles = "USER")
    void cartRegistrationSuccess() throws Exception {

        String bearerAccessToken = "Bearer accessToken";

        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .httpStatus(HttpStatus.CREATED)
                .status(ResponseStatus.SUCCESS.getDescription())
                .message(SuccessCode.CART_REGISTRATION_SUCCESS.getDescription())
                .data(null)
                .build();

        //given
        given(cartRegistrationService.cartRegistrationResponse(anyString(), anyString()))
                .willReturn(commonResponse);

        //when
        //then
        mvc.perform(post("/api/v1/user/cart/registration/{productName}", "상품명")
                        .with(csrf())
                        .header("Authorization", bearerAccessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(ResponseStatus.SUCCESS.getDescription()))
                .andExpect(jsonPath("$.message").value(SuccessCode.CART_REGISTRATION_SUCCESS.getDescription()))
                .andDo(print());
    }

    @Test
    @DisplayName("[API] 장바구니 상품 등록 - 유저 권한 없음")
    @WithMockUser(roles = "USER")
    void cartRegistrationFail1() throws Exception {

        String bearerAccessToken = "Bearer accessToken";

        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .status(ResponseStatus.FAIL.getDescription())
                .message(ErrorCode.AUTHORITY_NOT_USER.getDescription())
                .data(null)
                .build();

        //given
        given(cartRegistrationService.cartRegistrationResponse(anyString(), anyString()))
                .willReturn(commonResponse);

        //when
        //then
        mvc.perform(post("/api/v1/user/cart/registration/{productName}", "상품명")
                        .with(csrf())
                        .header("Authorization", bearerAccessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(ResponseStatus.FAIL.getDescription()))
                .andExpect(jsonPath("$.message").value(ErrorCode.AUTHORITY_NOT_USER.getDescription()))
                .andDo(print());
    }

    @Test
    @DisplayName("[API] 장바구니 상품 등록 - 판매 물품 없음")
    @WithMockUser(roles = "USER")
    void cartRegistrationFail2() throws Exception {

        String bearerAccessToken = "Bearer accessToken";

        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .status(ResponseStatus.FAIL.getDescription())
                .message(ErrorCode.PRODUCT_NOT_FOUND.getDescription())
                .data(null)
                .build();

        //given
        given(cartRegistrationService.cartRegistrationResponse(anyString(), anyString()))
                .willReturn(commonResponse);

        //when
        //then
        mvc.perform(post("/api/v1/user/cart/registration/{productName}", "상품명")
                        .with(csrf())
                        .header("Authorization", bearerAccessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(ResponseStatus.FAIL.getDescription()))
                .andExpect(jsonPath("$.message").value(ErrorCode.PRODUCT_NOT_FOUND.getDescription()))
                .andDo(print());
    }


}