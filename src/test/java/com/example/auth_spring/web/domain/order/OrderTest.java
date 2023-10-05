package com.example.auth_spring.web.domain.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderTest {

    @Test
    @DisplayName("[Domain] 주문 등록 도메인 테스트")
    void createOrder() {
        //given
        String orderName = "abcdefgh";
        Integer count = 1;
        Long totalPrice = 10000L;


        Order order = Order.builder()
                .count(count)
                .totalPrice(totalPrice)
                .orderName(orderName)
                .build();

        //when
        //then
        assertThat(order.getOrderName()).isEqualTo(orderName);
        assertThat(order.getTotalPrice()).isEqualTo(totalPrice);
        assertThat(order.getCount()).isEqualTo(count);

    }
}