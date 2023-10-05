package com.example.auth_spring.service.user.order.Inquiry;

import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.service.user.token.TokenService;
import com.example.auth_spring.web.domain.brand.Brand;
import com.example.auth_spring.web.domain.order.Order;
import com.example.auth_spring.web.domain.order.OrderRepository;
import com.example.auth_spring.web.domain.product.Product;
import com.example.auth_spring.web.dto.common.Pagination;
import com.example.auth_spring.web.dto.order.OrderProductAllListResponseDto;
import com.example.auth_spring.web.dto.order.OrderProductAllResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class OrderProductAllInquiryServiceTest {

    private TokenService tokenService;
    private OrderRepository orderRepository;
    private CommonService commonService;
    private OrderProductAllInquiryService orderProductAllInquiryService;

    private Product product;
    private Order order;
    private Brand brand;

    @BeforeEach
    void setup() {
        tokenService = mock(TokenService.class);
        orderRepository = mock(OrderRepository.class);
        orderProductAllInquiryService = new OrderProductAllInquiryService(tokenService, commonService, orderRepository);
    }

    @Test
    @DisplayName("[Service] 주문 정보 전체 조회 성공")
    void orderProductAllInquirySuccess() {

        String bearerAccessToken = "Bearer accessToken";
        String email = "email";

        brand = new Brand();
        ReflectionTestUtils.setField(brand, "name", "나이키");

        product = new Product();
        ReflectionTestUtils.setField(product, "name", "나이키 맨투맨");

        order = new Order();
        ReflectionTestUtils.setField(order, "orderName", "orderName");
        ReflectionTestUtils.setField(order, "totalPrice", 103000L);
        ReflectionTestUtils.setField(order, "count", 1);

        List<OrderProductAllResponseDto> orderProductAllList = List.of(OrderProductAllResponseDto.builder()
                .orderName(order.getOrderName())
                .productName(product.getName())
                .orderProductCount(order.getCount())
                .totalOrderPrice(order.getTotalPrice())
                .productBrand(brand.getName())
                .build());

        Page<OrderProductAllResponseDto> page = new PageImpl<>(orderProductAllList);

        //given
        given(tokenService.accessTokenEmail(anyString()))
                .willReturn(email);
        given(orderRepository.findAllOrderList(anyString(), any()))
                .willReturn(page);

        //when
        OrderProductAllListResponseDto result
                = orderProductAllInquiryService.orderProductAllList(bearerAccessToken, 1, 10, "modifiedAt");

        //then
        Pagination pagination = result.getPagination();
        assertThat(pagination.getPageNo()).isEqualTo(0);
        assertThat(pagination.getTotalPages()).isEqualTo(1);
        assertThat(pagination.getTotalElements()).isEqualTo(1);
        assertThat(pagination.isLastPage()).isEqualTo(true);
        assertThat(result.getOrderProductAllList().get(0).getOrderName())
                .isEqualTo(order.getOrderName());
        assertThat(result.getOrderProductAllList().get(0).getOrderProductCount())
                .isEqualTo(order.getCount());
        assertThat(result.getOrderProductAllList().get(0).getTotalOrderPrice())
                .isEqualTo(order.getTotalPrice());
        assertThat(result.getOrderProductAllList().get(0).getProductName())
                .isEqualTo(product.getName());
        assertThat(result.getOrderProductAllList().get(0).getProductBrand())
                .isEqualTo(brand.getName());
    }
}