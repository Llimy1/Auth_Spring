package com.example.auth_spring.web.controller.user.cart.delete;

import com.example.auth_spring.security.jwt.service.JwtProvider;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.service.user.cart.delete.CartDeleteService;import com.example.auth_spring.service.user.cart.inquiry.CartInquiryService;
import com.example.auth_spring.service.user.token.TokenService;
import com.example.auth_spring.type.ResponseStatus;
import com.example.auth_spring.type.SuccessCode;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartDeleteController.class)
class CartDeleteControllerTest {

    @MockBean
    private CartDeleteService cartDeleteService;

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
    @DisplayName("[API] 장바구니 상품 삭제 성공")
    @WithMockUser(roles = "USER")
    void cartDeleteSuccess() throws Exception {

        String bearerAccessToken = "Bearer accessToken";

        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .httpStatus(HttpStatus.OK)
                .status(ResponseStatus.SUCCESS.getDescription())
                .message(SuccessCode.CART_DELETE_SUCCESS.getDescription())
                .data(null)
                .build();

        //given
        given(cartDeleteService.cartDeleteResponse(anyString(), anyString()))
                .willReturn(commonResponse);

        //when
        //then
        mvc.perform(delete("/api/v1/user/cart/{productName}", "삭제 상품명")
                        .with(csrf())
                        .header("Authorization", bearerAccessToken)
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ResponseStatus.SUCCESS.getDescription()))
                .andExpect(jsonPath("$.message").value(SuccessCode.CART_DELETE_SUCCESS.getDescription()))
                .andDo(print());
    }
}