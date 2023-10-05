package com.example.auth_spring.service.all.search;

import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.web.domain.brand.Brand;
import com.example.auth_spring.web.domain.category.Category;
import com.example.auth_spring.web.domain.product.Product;
import com.example.auth_spring.web.domain.product.ProductRepository;
import com.example.auth_spring.web.domain.subcategory.SubCategory;
import com.example.auth_spring.web.domain.user.User;
import com.example.auth_spring.web.dto.common.Pagination;
import com.example.auth_spring.web.dto.product.ProductListResponseDto;
import com.example.auth_spring.web.dto.search.SearchProductListResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class SearchProductServiceTest {

    private ProductRepository productRepository;
    private CommonService commonService;
    private SearchProductService searchProductService;
    private Product product;
    private User user;
    private Brand brand;
    @BeforeEach
    void setup() {
        productRepository = mock(ProductRepository.class);
        searchProductService = new SearchProductService(productRepository, commonService);
    }

    @Test
    @DisplayName("[Service] 검색 상품 조회 성공")
    void searchProductSuccess() {

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
                .name("나이키 맨투맨")
                .price(10000L)
                .build();

        List<Product> productList = new ArrayList<>(Collections.singleton(product));
        Page<Product> page = new PageImpl<>(productList);

        //given
        given(productRepository.findAllByNameContaining(any(), any()))
                .willReturn(page);

        //when
        SearchProductListResponseDto searchProductListResponseDto = searchProductService.searchProductList("나이키", 1, 10 ,"modifiedAt");

        //then
        Pagination pagination = searchProductListResponseDto.getPagination();
        assertThat(pagination.getPageNo()).isEqualTo(0);
        assertThat(pagination.getTotalPages()).isEqualTo(1);
        assertThat(pagination.getTotalElements()).isEqualTo(1);
        assertThat(pagination.isLastPage()).isEqualTo(true);
        assertThat(searchProductListResponseDto.getSearchProductList().get(0).getProductName())
                .isEqualTo("나이키 맨투맨");
        assertThat(searchProductListResponseDto.getSearchProductList().get(0).getProductPrice())
                .isEqualTo(10000L);
        assertThat(searchProductListResponseDto.getSearchProductList().get(0).getBrandName())
                .isEqualTo("나이키");
    }

}