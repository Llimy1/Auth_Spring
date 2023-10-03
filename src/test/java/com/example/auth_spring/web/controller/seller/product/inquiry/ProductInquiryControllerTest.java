package com.example.auth_spring.web.controller.seller.product.inquiry;

import com.example.auth_spring.security.jwt.service.JwtProvider;
import com.example.auth_spring.security.jwt.service.TokenService;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.service.seller.inquiry.ProductInquiryService;
import com.example.auth_spring.service.seller.registration.ProductRegistrationService;
import com.example.auth_spring.type.ResponseStatus;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.domain.category.Category;
import com.example.auth_spring.web.domain.product.Product;
import com.example.auth_spring.web.domain.subcategory.SubCategory;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.Pagination;
import com.example.auth_spring.web.dto.product.ProductIdResponseDto;
import com.example.auth_spring.web.dto.product.ProductListResponseDto;
import com.example.auth_spring.web.dto.product.ProductResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductInquiryController.class)
class ProductInquiryControllerTest {


    @MockBean
    private ProductInquiryService productInquiryService;

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
    @DisplayName("[API] 판매자 상품 조회 성공")
    @WithMockUser(roles = "SELLER")
    void productInquirySuccess() throws Exception {

        String bearerAccessToken = "Bearer accessToken";

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

        ProductResponseDto productResponseDto = ProductResponseDto.builder()
                .product(product)
                .build();
        List<ProductResponseDto> productList = new ArrayList<>(Collections.singleton(productResponseDto));

        Page<ProductResponseDto> page = new PageImpl<>(productList);

        Pagination pagination = Pagination.builder()
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .pageNo(page.getNumber())
                .isLastPage(page.isLast())
                .build();


        ProductListResponseDto productListResponseDto = ProductListResponseDto.builder()
                .productList(productList)
                .pagination(pagination)
                .build();

        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .httpStatus(HttpStatus.OK)
                .status(ResponseStatus.SUCCESS.getDescription())
                .message(SuccessCode.SELLER_PRODUCT_INQUIRY_SUCCESS.getDescription())
                .data(productListResponseDto)
                .build();


        //given
        given(productInquiryService.productInquiryResponse(any(), anyInt(), anyInt(), any()))
                .willReturn(commonResponse);


        //when
        //then
        mvc.perform(get("/api/v1/seller/product/getList")
                .with(csrf())
                        .header("Authorization", bearerAccessToken)
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ResponseStatus.SUCCESS.getDescription()))
                .andExpect(jsonPath("$.message").value(SuccessCode.SELLER_PRODUCT_INQUIRY_SUCCESS.getDescription()))
                .andExpect(jsonPath("$.data.productList[0].productName").value("옷"))
                .andExpect(jsonPath("$.data.productList[0].productPrice").value(10000L))
                .andDo(print());

    }

}