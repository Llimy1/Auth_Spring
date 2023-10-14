package com.example.auth_spring.service.user.cart.inquiry;

import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.service.user.token.TokenService;
import com.example.auth_spring.type.Role;
import com.example.auth_spring.web.domain.brand.Brand;
import com.example.auth_spring.web.domain.cart.Cart;
import com.example.auth_spring.web.domain.cart.CartRepository;
import com.example.auth_spring.web.domain.category.Category;
import com.example.auth_spring.web.domain.option.Option;
import com.example.auth_spring.web.domain.product.Product;
import com.example.auth_spring.web.domain.productoption.ProductOption;
import com.example.auth_spring.web.domain.subcategory.SubCategory;
import com.example.auth_spring.web.domain.user.User;
import com.example.auth_spring.web.dto.cart.CartListResponseDto;
import com.example.auth_spring.web.dto.cart.CartResponseDto;
import com.example.auth_spring.web.dto.common.Pagination;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class CartInquiryServiceTest {

    private CartRepository cartRepository;
    private TokenService tokenService;
    private CommonService commonService;
    private CartInquiryService cartInquiryService;
    private User user;
    private Product product;
    private Brand brand;

    @BeforeEach
    void setup() {
        cartRepository = mock(CartRepository.class);
        tokenService = mock(TokenService.class);
        cartInquiryService = new CartInquiryService(tokenService, commonService, cartRepository);
    }

    @Test
    @DisplayName("[Service] 장바구니 상품 조회 성공")
    void cartInquirySuccess() {

        String bearerAccessToken = "Bearer accessToken";

        user = new User();
        ReflectionTestUtils.setField(user, "id", 1L);
        ReflectionTestUtils.setField(user, "role", Role.USER);

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
        ReflectionTestUtils.setField(product, "id", 1L);

        Option option = Option.builder()
                .user(user)
                .size("XL")
                .color("RED")
                .build();

        ReflectionTestUtils.setField(option, "id", 1L);

        ProductOption productOption = ProductOption.builder()
                .option(option)
                .product(product)
                .build();

        Cart cart = Cart.builder()
                .user(user)
                .productOption(productOption)
                .build();

        List<CartResponseDto> cartList = List.of(CartResponseDto.builder()
                .productName(product.getName())
                .productPrice(product.getPrice())
                .brandName(product.getBrand().getName())
                .isDiscount(product.getIsDiscount())
                .discountRate(product.getDiscountRate())
                .build());

        Page<CartResponseDto> page = new PageImpl<>(cartList);


        given(tokenService.accessTokenEmail(anyString()))
                .willReturn("email");
        given(cartRepository.findCartByUserEmail(anyString(), any()))
                .willReturn(page);


        //when
        CartListResponseDto cartListResponseDto = cartInquiryService.cartInquiry(bearerAccessToken, 1, 10, "modified");


        //then
        Pagination pagination = cartListResponseDto.getPagination();
        assertThat(pagination.getPageNo()).isEqualTo(0);
        assertThat(pagination.getTotalPages()).isEqualTo(1);
        assertThat(pagination.getTotalElements()).isEqualTo(1);
        assertThat(pagination.isLastPage()).isEqualTo(true);
        assertThat(cartListResponseDto.getCartList().get(0).getProductName())
                .isEqualTo(cartList.get(0).getProductName());
        assertThat(cartListResponseDto.getCartList().get(0).getProductPrice())
                .isEqualTo(cartList.get(0).getProductPrice());
        assertThat(cartListResponseDto.getCartList().get(0).getBrandName())
                .isEqualTo(cartList.get(0).getBrandName());
        assertThat(cartListResponseDto.getCartList().get(0).getIsDiscount())
                .isEqualTo(cartList.get(0).getIsDiscount());
        assertThat(cartListResponseDto.getCartList().get(0).getDiscountRate())
                .isEqualTo(cartList.get(0).getDiscountRate());

    }

}