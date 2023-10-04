package com.example.auth_spring.service.all.inquiry;

import com.example.auth_spring.service.all.inquiry.AllProductInquiryService;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.web.domain.category.Category;
import com.example.auth_spring.web.domain.product.Product;
import com.example.auth_spring.web.domain.product.ProductRepository;
import com.example.auth_spring.web.domain.subcategory.SubCategory;
import com.example.auth_spring.web.dto.common.Pagination;
import com.example.auth_spring.web.dto.product.ProductListResponseDto;
import com.example.auth_spring.web.dto.product.ProductResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class AllProductInquiryServiceTest {

    private ProductRepository productRepository;
    private CommonService commonService;
    private AllProductInquiryService allProductInquiryService;
    private Product product;

    @BeforeEach
    void setup() {
        productRepository = mock(ProductRepository.class);
        allProductInquiryService = new AllProductInquiryService(productRepository, commonService);
    }

    @Test
    @DisplayName("[Service] 전체 상품 조회 성공")
    void allProductInquirySuccess() {

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

//        List<Product> productList = new ArrayList<>(Collections.singleton(product));
//        Page<Product> page = new PageImpl<>(productList);

        List<ProductResponseDto> productList = List.of(ProductResponseDto.builder()
                .productName(product.getName())
                .productPrice(product.getPrice())
                .build());

        Page<ProductResponseDto> page = new PageImpl<>(productList);


        //given
        given(productRepository.findProductAllList(any()))
                .willReturn(page);

        //when
        ProductListResponseDto productListResponseDto = allProductInquiryService.allProductInquiry(1, 10, "modifiedAt");

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