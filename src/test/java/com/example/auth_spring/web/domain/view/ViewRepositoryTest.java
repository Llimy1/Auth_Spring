package com.example.auth_spring.web.domain.view;

import com.example.auth_spring.web.domain.product.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ViewRepositoryTest {


    @Autowired
    private ViewRepository viewRepository;

    @Test
    @DisplayName("[Repository] 조회수 생성")
    void createView() {

        String productName1 = "옷";
        Long productPrice1 = 10000L;
        Integer deliveryPrice1 = 3000;
        Integer discountRate1 = 10;
        Boolean isDiscount1 = true;

        String productName2 = "옷가지";
        Long productPrice2 = 100000L;
        Integer deliveryPrice2 = 4000;
        Boolean isDiscount2 = false;

        Long count1 = 0L;
        Long count2 = 0L;

        Product product1 = Product.builder()
                .name(productName1)
                .price(productPrice1)
                .deliveryPrice(deliveryPrice1)
                .isDiscount(isDiscount1)
                .discountRate(discountRate1)
                .build();

        Product product2 = Product.builder()
                .name(productName2)
                .price(productPrice2)
                .deliveryPrice(deliveryPrice2)
                .isDiscount(isDiscount2)
                .build();

        //given
        View view1 = View.builder()
                .product(product1)
                .count(count1)
                .build();

        View view2 = View.builder()
                .product(product2)
                .count(count2)
                .build();

        //when
        View result1 = viewRepository.save(view1);
        View result2 = viewRepository.save(view2);

        //then
        assertThat(result1.getCount()).isEqualTo(count1);
        assertThat(result2.getCount()).isEqualTo(count2);
    }



}