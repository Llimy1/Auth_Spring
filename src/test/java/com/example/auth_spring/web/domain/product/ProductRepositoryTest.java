package com.example.auth_spring.web.domain.product;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("[Repository] 상품 등록 하기")
    void createProduct() {
        String productName1 = "옷";
        Long productPrice1 = 10000L;
        Integer deliveryPrice1 = 3000;
        Integer discountRate1 = 10;
        Boolean isDiscount1 = true;

        String productName2 = "옷가지";
        Long productPrice2 = 100000L;
        Integer deliveryPrice2 = 4000;
        Boolean isDiscount2 = false;

        //given
        Product product1 = Product.builder()
                .name(productName1)
                .price(productPrice1)
                .deliveryPrice(deliveryPrice1)
                .isDiscount(isDiscount1)
                .discountRate(discountRate1)
                .likeCount(0L)
                .build();

        Product product2 = Product.builder()
                .name(productName2)
                .price(productPrice2)
                .deliveryPrice(deliveryPrice2)
                .isDiscount(isDiscount2)
                .likeCount(0L)
                .build();

        //when
        Product result1 = productRepository.save(product1);
        Product result2 = productRepository.save(product2);

        //then
        assertThat(result1.getName()).isEqualTo(productName1);
        assertThat(result2.getName()).isEqualTo(productName2);

    }
}