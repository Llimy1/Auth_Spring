package com.example.auth_spring.web.controller.user.order.Inquiry;

import com.example.auth_spring.security.jwt.service.JwtProvider;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.service.user.order.Inquiry.OrderProductDetailInquiryService;
import com.example.auth_spring.service.user.order.registration.OrderProductRegistrationService;
import com.example.auth_spring.service.user.token.TokenService;
import com.example.auth_spring.type.ResponseStatus;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.domain.address.Address;
import com.example.auth_spring.web.domain.brand.Brand;
import com.example.auth_spring.web.domain.order.Order;
import com.example.auth_spring.web.domain.product.Product;
import com.example.auth_spring.web.domain.user.User;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.order.OrderProductDetailResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderProductDetailInquiryController.class)
class OrderProductDetailInquiryControllerTest {

    @MockBean
    private OrderProductDetailInquiryService orderProductDetailInquiryService;

    @MockBean
    private CommonService commonService;

    @MockBean
    private JwtProvider jwtProvider;

    @MockBean
    private TokenService tokenService;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mvc;

    private User user;
    private Product product;
    private Address address;
    private Order order;
    private Brand brand;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("[API] 주문 상세 정보 조회 성공")
    @WithMockUser(roles = "USER")
    void orderProductDetailInquirySuccess() throws Exception {

        String bearerAccessToken = "Bearer accessToken";

        brand = new Brand();
        ReflectionTestUtils.setField(brand, "name", "나이키");

        user = new User();
        ReflectionTestUtils.setField(user, "name", "홍길동");

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

        OrderProductDetailResponseDto orderProductDetailResponseDto = OrderProductDetailResponseDto.builder()
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

        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .httpStatus(HttpStatus.OK)
                .status(ResponseStatus.SUCCESS.getDescription())
                .message(SuccessCode.ORDER_PRODUCT_DETAIL_INQUIRY_SUCCESS.getDescription())
                .data(orderProductDetailResponseDto)
                .build();

        //given
        given(orderProductDetailInquiryService.orderProductDetailResponse(anyString(), anyString()))
                .willReturn(commonResponse);

        //when
        //then
        mvc.perform(get("/api/v1/user/order/{orderName}/getDetail", "주문 번호")
                        .with(csrf())
                        .header("Authorization", bearerAccessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ResponseStatus.SUCCESS.getDescription()))
                .andExpect(jsonPath("$.message").value(SuccessCode.ORDER_PRODUCT_DETAIL_INQUIRY_SUCCESS.getDescription()))
                .andExpect(jsonPath("$.data.userName").value(user.getName()))
                .andExpect(jsonPath("$.data.productName").value(product.getName()))
                .andExpect(jsonPath("$.data.productPrice").value(product.getPrice()))
                .andExpect(jsonPath("$.data.productBrand").value(brand.getName()))
                .andExpect(jsonPath("$.data.deliveryPrice").value(product.getDeliveryPrice()))
                .andExpect(jsonPath("$.data.totalOrderPrice").value(order.getTotalPrice()))
                .andExpect(jsonPath("$.data.zipCode").value(address.getZipCode()))
                .andExpect(jsonPath("$.data.streetAddress").value(address.getStreetAddress()))
                .andExpect(jsonPath("$.data.detailAddress").value(address.getDetailAddress()))
                .andExpect(jsonPath("$.data.orderProductCount").value(order.getCount()))
                .andDo(print());
    }
}