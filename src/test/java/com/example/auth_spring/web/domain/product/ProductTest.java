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
        Integer deliveryPrice = 3000;
        Integer discountRate1 = 10;
        Boolean isDiscount1 = true;
        Long likeCount = 0L;

        Product product = Product.builder()
                .name(productName)
                .price(productPrice)
                .deliveryPrice(deliveryPrice)
                .isDiscount(isDiscount1)
                .discountRate(discountRate1)
                .likeCount(likeCount)
                .build();

        //when
        //then
        assertThat(product.getName()).isEqualTo(productName);
        assertThat(product.getPrice()).isEqualTo(productPrice);
        assertThat(product.getDeliveryPrice()).isEqualTo(deliveryPrice);
        assertThat(product.getDiscountRate()).isEqualTo(discountRate1);
        assertThat(product.getIsDiscount()).isEqualTo(isDiscount1);
        assertThat(product.getLikeCount()).isEqualTo(likeCount);


    }

}