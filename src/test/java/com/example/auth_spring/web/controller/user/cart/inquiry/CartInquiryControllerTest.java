package com.example.auth_spring.web.controller.user.cart.inquiry;

import com.example.auth_spring.security.jwt.service.JwtProvider;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.service.seller.inquiry.ProductInquiryService;
import com.example.auth_spring.service.user.cart.inquiry.CartInquiryService;
import com.example.auth_spring.service.user.token.TokenService;
import com.example.auth_spring.type.ResponseStatus;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.domain.cart.Cart;
import com.example.auth_spring.web.domain.category.Category;
import com.example.auth_spring.web.domain.product.Product;
import com.example.auth_spring.web.domain.subcategory.SubCategory;
import com.example.auth_spring.web.domain.user.User;
import com.example.auth_spring.web.dto.cart.CartListResponseDto;
import com.example.auth_spring.web.dto.cart.CartResponseDto;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.Pagination;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartInquiryController.class)
class CartInquiryControllerTest {

    @MockBean
    private CartInquiryService cartInquiryService;

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
    @DisplayName("[API] 장바구니 상품 조회 성공")
    @WithMockUser(roles = "USER")
    void cartInquirySuccess() throws Exception {

        String bearerAccessToken = "Bearer accessToken";

        User user = new User();
        ReflectionTestUtils.setField(user, "id", 1L);

        Product product = Product.builder()
                .subCategory(SubCategory.builder()
                        .category(Category.builder()
                                .name("의류")
                                .build())
                        .name("맨투맨")
                        .build())
                .name("옷")
                .price(10000L)
                .build();
        ReflectionTestUtils.setField(product, "id", 1L);

        Cart cart = Cart.builder()
                .product(product)
                .user(user)
                .build();

        CartResponseDto cartResponseDto = CartResponseDto.builder()
                .productName(cart.getProduct().getName())
                .productPrice(cart.getProduct().getPrice())
                .build();

        List<CartResponseDto> cartResponseDtoList = new ArrayList<>(Collections.singleton(cartResponseDto));

        Page<CartResponseDto> page = new PageImpl<>(cartResponseDtoList);

        Pagination pagination = Pagination.builder()
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .pageNo(page.getNumber())
                .isLastPage(page.isLast())
                .build();

        CartListResponseDto cartListResponseDto = CartListResponseDto.builder()
                .cartList(page.getContent())
                .pagination(pagination)
                .build();

        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .httpStatus(HttpStatus.OK)
                .status(ResponseStatus.SUCCESS.getDescription())
                .message(SuccessCode.CART_INQUIRY_SUCCESS.getDescription())
                .data(cartListResponseDto)
                .build();

        //given
        given(cartInquiryService.cartInquiryResponse(anyString(), anyInt(), anyInt(), any()))
                .willReturn(commonResponse);

        //when
        //then
        mvc.perform(get("/api/v1/user/cart/getList")
                        .with(csrf())
                        .header("Authorization", bearerAccessToken)
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ResponseStatus.SUCCESS.getDescription()))
                .andExpect(jsonPath("$.message").value(SuccessCode.CART_INQUIRY_SUCCESS.getDescription()))
                .andExpect(jsonPath("$.data.cartList[0].productName").value("옷"))
                .andExpect(jsonPath("$.data.cartList[0].productPrice").value(10000L))
                .andExpect(jsonPath("$.data.pagination.totalPages").value(1))
                .andExpect(jsonPath("$.data.pagination.totalElements").value(1))
                .andExpect(jsonPath("$.data.pagination.pageNo").value(0))
                .andExpect(jsonPath("$.data.pagination.lastPage").value(true))
                .andDo(print());
    }

}