package com.example.auth_spring.web.controller.all.inquiry;

import com.example.auth_spring.security.jwt.service.JwtProvider;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.service.all.inquiry.AllProductInquiryService;
import com.example.auth_spring.type.ResponseStatus;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.controller.all.inquiry.AllProductInquiryController;
import com.example.auth_spring.web.domain.category.Category;
import com.example.auth_spring.web.domain.product.Product;
import com.example.auth_spring.web.domain.subcategory.SubCategory;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.Pagination;
import com.example.auth_spring.web.dto.product.ProductListResponseDto;
import com.example.auth_spring.web.dto.product.ProductResponseDto;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AllProductInquiryController.class)
class AllProductInquiryControllerTest {

    @MockBean
    private AllProductInquiryService allProductInquiryService;

    @MockBean
    private CommonService commonService;

    @MockBean
    private JwtProvider jwtProvider;

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
    @DisplayName("[API] 전체 상품 조회 성공")
    @WithMockUser(roles = "USER")
    void allProductInquirySuccess() throws Exception {

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
                .productName(product.getName())
                .productPrice(product.getPrice())
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
                .message(SuccessCode.ALL_PRODUCT_INQUIRY_SUCCESS.getDescription())
                .data(productListResponseDto)
                .build();


        //given
        given(allProductInquiryService.allProductInquiryResponse(anyInt(), anyInt(), anyString()))
                .willReturn(commonResponse);

        //when
        //then
        mvc.perform(get("/api/v1/all/product/getAllList")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ResponseStatus.SUCCESS.getDescription()))
                .andExpect(jsonPath("$.message").value(SuccessCode.ALL_PRODUCT_INQUIRY_SUCCESS.getDescription()))
                .andExpect(jsonPath("$.data.productList[0].productName").value("옷"))
                .andExpect(jsonPath("$.data.productList[0].productPrice").value(10000L))
                .andExpect(jsonPath("$.data.pagination.totalPages").value(1))
                .andExpect(jsonPath("$.data.pagination.totalElements").value(1))
                .andExpect(jsonPath("$.data.pagination.pageNo").value(0))
                .andExpect(jsonPath("$.data.pagination.lastPage").value(true))
                .andDo(print());
    }
}