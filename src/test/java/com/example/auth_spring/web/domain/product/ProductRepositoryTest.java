package com.example.auth_spring.web.domain.product;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void beforeCleanup() {
        productRepository.deleteAll();
    }

    @AfterEach
    public void afterCleanup() {
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("[Repository] 상품 등록 하기")
    void createProduct() {
        String productName1 = "옷";
        Long productPrice1 = 10000L;

        String productName2 = "옷가지";
        Long productPrice2 = 100000L;

        //given
        Product product1 = Product.builder()
                .name(productName1)
                .price(productPrice1)
                .build();

        Product product2 = Product.builder()
                .name(productName2)
                .price(productPrice2)
                .build();

        //when
        Product result1 = productRepository.save(product1);
        Product result2 = productRepository.save(product2);

        //then
        assertThat(result1.getName()).isEqualTo(productName1);
        assertThat(result2.getName()).isEqualTo(productName2);

    }
}