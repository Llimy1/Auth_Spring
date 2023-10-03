package com.example.auth_spring.service.seller.inquiry;

import com.example.auth_spring.security.jwt.service.TokenService;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.type.Role;
import com.example.auth_spring.web.domain.category.Category;
import com.example.auth_spring.web.domain.product.Product;
import com.example.auth_spring.web.domain.product.ProductRepository;
import com.example.auth_spring.web.domain.subcategory.SubCategory;
import com.example.auth_spring.web.domain.user.User;
import com.example.auth_spring.web.dto.common.Pagination;
import com.example.auth_spring.web.dto.product.ProductListResponseDto;
import com.example.auth_spring.web.dto.product.ProductResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ProductInquiryServiceTest {

    private ProductRepository productRepository;
    private TokenService tokenService;
    private CommonService commonService;
    private ProductInquiryService productInquiryService;
    private User user;
    private Product product;

    @BeforeEach
    void setup() {
        productRepository = mock(ProductRepository.class);
        tokenService = mock(TokenService.class);
        productInquiryService = new ProductInquiryService(productRepository, tokenService, commonService);
    }


    @Test
    @DisplayName("[Service] 판매자 상품 조회")
    void productInquiry() {

        String bearerAccessToken = "Bearer accessToken";

        user = new User();
        ReflectionTestUtils.setField(user, "id", 1L);
        ReflectionTestUtils.setField(user, "role", Role.SELLER);

        product = Product.builder()
                .subCategory(SubCategory.builder()
                        .category(Category.builder()
                                .name("의류")
                                .build())
                        .name("맨투맨")
                        .build())
                .name("옷")
                .price(10000L)
                .build();

        List<Product> productList = new ArrayList<>(Collections.singleton(product));
        Page<Product> page = new PageImpl<>(productList);


        //given
        given(productRepository.findAllByUserId(any(), any()))
                .willReturn(page);

        given(tokenService.findUser(anyString())).willReturn(user);

        //when
        ProductListResponseDto productListResponseDto = productInquiryService.productInquiry(bearerAccessToken, 1, 10, "modifiedAt");

        //then
        Pagination pagination = productListResponseDto.getPagination();
        assertThat(pagination.getPageNo()).isEqualTo(0);
        assertThat(pagination.getTotalPages()).isEqualTo(1);
        assertThat(pagination.getTotalElements()).isEqualTo(1);
        assertThat(pagination.isLastPage()).isEqualTo(true);
        assertThat(productListResponseDto.getProductList().get(0).getProductName())
                .isEqualTo("옷");
        assertThat(productListResponseDto.getProductList().get(0).getProductPrice())
                .isEqualTo(10000L);



    }
}