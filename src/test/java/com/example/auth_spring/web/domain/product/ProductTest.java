package com.example.auth_spring.web.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductTest {

    @Test
    @DisplayName("[Domain] 상품 등록 도메인 테스트")
    void createProduct() {
        //given
        String productName = "옷";
        Long productPrice = 10000L;

        Product product = Product.builder()
                .name(productName)
                .price(productPrice)
                .build();

        //when
        //then
        assertThat(product.getName()).isEqualTo(productName);
        assertThat(product.getPrice()).isEqualTo(productPrice);
    }

}