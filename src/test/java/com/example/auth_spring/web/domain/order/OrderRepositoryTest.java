package com.example.auth_spring.web.domain.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("[Repository] 주문 등록 하기")
    void createOrder() {

        String orderName1 = "abcdefgh1";
        Integer count1 = 1;
        Long totalPrice1 = 10000L;

        String orderName2 = "abcdefgh2";
        Integer count2 = 2;
        Long totalPrice2 = 20000L;


        //given
        Order order1 = Order.builder()
                .orderName(orderName1)
                .count(count1)
                .totalPrice(totalPrice1)
                .build();

        Order order2 = Order.builder()
                .orderName(orderName2)
                .count(count2)
                .totalPrice(totalPrice2)
                .build();

        //when
        Order result1 = orderRepository.save(order1);
        Order result2 = orderRepository.save(order2);

        //then
        assertThat(result1.getOrderName()).isEqualTo(order1.getOrderName());
        assertThat(result2.getOrderName()).isEqualTo(order2.getOrderName());
    }


}