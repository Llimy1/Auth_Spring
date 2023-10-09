package com.example.auth_spring.web.domain.view;

import com.example.auth_spring.web.domain.product.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ViewTest {

    @Test
    @DisplayName("[Domain] 조회수 생성 도메인 테스트")
    void createView() {

        String productName = "옷";
        Long productPrice = 10000L;
        Integer deliveryPrice = 3000;
        Integer discountRate1 = 10;
        Boolean isDiscount1 = true;
        Long likeCount = 0L;

        Long viewCount = 0L;

        Product product = Product.builder()
                .name(productName)
                .price(productPrice)
                .deliveryPrice(deliveryPrice)
                .isDiscount(isDiscount1)
                .discountRate(discountRate1)
                .likeCount(likeCount)
                .build();

        //given
        View view = View.builder()
                .product(product)
                .count(viewCount)
                .build();

        //when
        //then
        assertThat(view.getCount()).isEqualTo(viewCount);
    }
}