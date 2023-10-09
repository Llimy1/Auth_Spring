package com.example.auth_spring.service.seller.inquiry.product;

import com.example.auth_spring.service.seller.inquiry.product.ProductInquiryService;
import com.example.auth_spring.service.user.token.TokenService;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.type.Role;
import com.example.auth_spring.web.domain.brand.Brand;
import com.example.auth_spring.web.domain.category.Category;
import com.example.auth_spring.web.domain.product.Product;
import com.example.auth_spring.web.domain.product.ProductRepository;
import com.example.auth_spring.web.domain.subcategory.SubCategory;
import com.example.auth_spring.web.domain.user.User;
import com.example.auth_spring.web.domain.view.View;
import com.example.auth_spring.web.dto.common.Pagination;
import com.example.auth_spring.web.dto.product.ProductListResponseDto;
import com.example.auth_spring.web.dto.product.ProductResponseDto;
import com.example.auth_spring.web.dto.seller.SellerProductListResponseDto;
import com.example.auth_spring.web.dto.seller.SellerProductResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
    private Brand brand;
    private View view;

    @BeforeEach
    void setup() {
        productRepository = mock(ProductRepository.class);
        tokenService = mock(TokenService.class);
        productInquiryService = new ProductInquiryService(productRepository, tokenService, commonService);
    }


    @Test
    @DisplayName("[Service] 판매자 상품 조회 성공")
    void productInquirySuccess() {

        String bearerAccessToken = "Bearer accessToken";

        user = new User();
        ReflectionTestUtils.setField(user, "id", 1L);
        ReflectionTestUtils.setField(user, "role", Role.SELLER);

        brand = Brand.builder()
                .user(user)
                .name("나이키")
                .build();

        String email = "email";

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
                .likeCount(0L)
                .build();

        view = View.builder()
                .product(product)
                .count(0L)
                .build();

        SellerProductResponseDto productResponseDto = SellerProductResponseDto.builder()
                .productName(product.getName())
                .productPrice(product.getPrice())
                .brandName(product.getBrand().getName())
                .isDiscount(product.getIsDiscount())
                .discountRate(product.getDiscountRate())
                .viewCount(view.getCount())
                .likeCount(product.getLikeCount())
                .build();

        List<SellerProductResponseDto> productList = List.of(productResponseDto);
        Page<SellerProductResponseDto> page = new PageImpl<>(productList);

        given(productRepository.findProductByUserEmail(anyString(), any()))
                .willReturn(page);
        given(tokenService.accessTokenEmail(anyString())).willReturn(email);

        //when
        SellerProductListResponseDto productListResponseDto = productInquiryService.productInquiry(bearerAccessToken, 1, 10, "modifiedAt");

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
        assertThat(productListResponseDto.getProductList().get(0).getViewCount())
                .isEqualTo(productList.get(0).getViewCount());
        assertThat(productListResponseDto.getProductList().get(0).getLikeCount())
                .isEqualTo(productList.get(0).getLikeCount());
    }
}