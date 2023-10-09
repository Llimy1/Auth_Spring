package com.example.auth_spring.service.all.inquiry;

import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.web.domain.brand.Brand;
import com.example.auth_spring.web.domain.category.Category;
import com.example.auth_spring.web.domain.option.Option;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

class SubCategoryProductInquiryServiceTest {
    private ProductRepository productRepository;
    private CommonService commonService;
    private SubCategoryProductInquiryService subCategoryProductInquiryService;
    private Product product;
    private User user;
    private Brand brand;

    @BeforeEach
    void setup() {
        productRepository = mock(ProductRepository.class);
        subCategoryProductInquiryService = new SubCategoryProductInquiryService(productRepository, commonService);
    }

    @Test
    @DisplayName("[Service] 서브 카테고리 별 상품 조회 성공")
    void subCategoryProductInquirySuccess() {

        user = new User();
        ReflectionTestUtils.setField(user, "id", 1L);

        brand = Brand.builder()
                .user(user)
                .name("나이키")
                .build();

        product = Product.builder()
                .subCategory(SubCategory.builder()
                        .category(Category.builder()
                                .name("의류")
                                .build())
                        .name("맨투맨")
                        .build())
                .brand(brand)
                .name("옷")
                .price(10000L)
                .deliveryPrice(3000)
                .isDiscount(true)
                .discountRate(10)
                .build();




        List<ProductResponseDto> productList = List.of(ProductResponseDto.builder()
                .productName(product.getName())
                .productPrice(product.getPrice())
                .brandName(product.getBrand().getName())
                .isDiscount(product.getIsDiscount())
                .discountRate(product.getDiscountRate())
                .build());

        Page<ProductResponseDto> page = new PageImpl<>(productList);

        //given
        given(productRepository.findProductListBySubCategoryName(anyString(), any()))
                .willReturn(page);

        //when
        ProductListResponseDto productListResponseDto = subCategoryProductInquiryService.subCategoryProductInquiry("맨투맨", 1, 10, "modifiedAt");


        //then
        Pagination pagination = productListResponseDto.getPagination();
        assertThat(pagination.getPageNo()).isEqualTo(0);
        assertThat(pagination.getTotalPages()).isEqualTo(1);
        assertThat(pagination.getTotalElements()).isEqualTo(1);
        assertThat(pagination.isLastPage()).isEqualTo(true);
        assertThat(productListResponseDto.getProductList().get(0).getProductName())
                .isEqualTo(productList.get(0).getProductName());
        assertThat(productListResponseDto.getProductList().get(0).getProductPrice())
                .isEqualTo(productList.get(0).getProductPrice());
        assertThat(productListResponseDto.getProductList().get(0).getBrandName())
                .isEqualTo(product.getBrand().getName());
        assertThat(productListResponseDto.getProductList().get(0).getIsDiscount())
                .isEqualTo(productList.get(0).getIsDiscount());
        assertThat(productListResponseDto.getProductList().get(0).getDiscountRate())
                .isEqualTo(productList.get(0).getDiscountRate());

    }
}