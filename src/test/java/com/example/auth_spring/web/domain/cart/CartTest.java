package com.example.auth_spring.web.domain.cart;

import com.example.auth_spring.web.domain.product.Product;
import com.example.auth_spring.web.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CartTest {


    @Test
    @DisplayName("[Domain] 장바구니 상품 등록 도메인 테스트")
    void crateCartSuccess() {

        User user = new User();
        ReflectionTestUtils.setField(user, "id", 1L);

        Product product = new Product();
        ReflectionTestUtils.setField(product, "id", 1L);

        //given
        Cart cart = Cart.builder()
                .user(user)
                .product(product)
                .build();

        //when
        //then
        assertThat(cart.getUser().getId()).isEqualTo(1L);
        assertThat(cart.getProduct().getId()).isEqualTo(1L);
    }
}