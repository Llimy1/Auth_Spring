package com.example.auth_spring.service.user.order.Inquiry;

import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.service.user.token.TokenService;
import com.example.auth_spring.web.domain.address.Address;
import com.example.auth_spring.web.domain.brand.Brand;
import com.example.auth_spring.web.domain.order.Order;
import com.example.auth_spring.web.domain.order.OrderRepository;
import com.example.auth_spring.web.domain.product.Product;
import com.example.auth_spring.web.domain.user.User;
import com.example.auth_spring.web.dto.order.OrderProductDetailResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class OrderProductDetailInquiryServiceTest {

    private TokenService tokenService;
    private CommonService commonService;
    private OrderRepository orderRepository;
    private OrderProductDetailInquiryService orderProductDetailInquiryService;
    private User user;
    private Product product;
    private Address address;
    private Order order;
    private Brand brand;
    private OrderProductDetailResponseDto orderProductDetailResponseDto;

    @BeforeEach
    void setup() {
        tokenService = mock(TokenService.class);
        orderRepository = mock(OrderRepository.class);
        orderProductDetailInquiryService = new OrderProductDetailInquiryService(tokenService, commonService, orderRepository);
    }

    @Test
    @DisplayName("[Service] 주문 상세 정보 조회 성공")
    void orderProductDetailInquirySuccess() {

        String bearerAccessToken = "Bearer accessToken";
        String orderName = "orderName";

        user = new User();
        ReflectionTestUtils.setField(user, "name", "홍길동");

        brand = new Brand();
        ReflectionTestUtils.setField(brand, "name", "나이키");

        product = new Product();
        ReflectionTestUtils.setField(product, "name", "나이키 맨투맨");
        ReflectionTestUtils.setField(product, "price", 100000L);
        ReflectionTestUtils.setField(product, "deliveryPrice", 3000);

        address = new Address();
        ReflectionTestUtils.setField(address, "zipCode", "12345");
        ReflectionTestUtils.setField(address, "streetAddress", "서울시 강남구");
        ReflectionTestUtils.setField(address, "detailAddress", "도곡로 1길 30");

        order = new Order();
        ReflectionTestUtils.setField(order, "count", 1);
        ReflectionTestUtils.setField(order, "totalPrice", 103000L);

        orderProductDetailResponseDto = OrderProductDetailResponseDto.builder()
                .userName(user.getName())
                .productName(product.getName())
                .productPrice(product.getPrice())
                .productBrand(brand.getName())
                .deliveryPrice(product.getDeliveryPrice())
                .zipCode(address.getZipCode())
                .streetAddress(address.getStreetAddress())
                .detailAddress(address.getDetailAddress())
                .orderProductCount(order.getCount())
                .totalOrderPrice(order.getTotalPrice())
                .build();


        //given
        given(orderRepository.findDetailOrder(anyString()))
                .willReturn(orderProductDetailResponseDto);

        //when
        OrderProductDetailResponseDto result =
                orderProductDetailInquiryService.orderProductDetail(bearerAccessToken, orderName);

        //then
        assertThat(result.getUserName()).isEqualTo(user.getName());
        assertThat(result.getProductName()).isEqualTo(product.getName());
        assertThat(result.getProductPrice()).isEqualTo(product.getPrice());
        assertThat(result.getProductBrand()).isEqualTo(brand.getName());
        assertThat(result.getDeliveryPrice()).isEqualTo(product.getDeliveryPrice());
        assertThat(result.getOrderProductCount()).isEqualTo(order.getCount());
        assertThat(result.getTotalOrderPrice()).isEqualTo(order.getTotalPrice());
        assertThat(result.getZipCode()).isEqualTo(address.getZipCode());
        assertThat(result.getStreetAddress()).isEqualTo(address.getStreetAddress());
        assertThat(result.getDetailAddress()).isEqualTo(address.getDetailAddress());
    }
}