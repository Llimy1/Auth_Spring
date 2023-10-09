package com.example.auth_spring.web.controller.user.order.Inquiry;

import com.example.auth_spring.security.jwt.service.JwtProvider;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.service.user.order.Inquiry.OrderProductAllInquiryService;
import com.example.auth_spring.service.user.order.Inquiry.OrderProductDetailInquiryService;
import com.example.auth_spring.service.user.token.TokenService;
import com.example.auth_spring.type.ResponseStatus;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.domain.brand.Brand;
import com.example.auth_spring.web.domain.order.Order;
import com.example.auth_spring.web.domain.product.Product;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.Pagination;
import com.example.auth_spring.web.dto.order.OrderProductAllListResponseDto;
import com.example.auth_spring.web.dto.order.OrderProductAllResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderProductAllInquiryController.class)
class OrderProductAllInquiryControllerTest {

    @MockBean
    private OrderProductAllInquiryService orderProductAllInquiryService;

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

    private Product product;
    private Order order;
    private Brand brand;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("[API] 주문 정보 전체 조회 성공")
    @WithMockUser(roles = "USER")
    void orderProductAllInquirySuccess() throws Exception {

        String bearerAccessToken = "Bearer accessToken";

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

        OrderProductAllListResponseDto orderProductAllListResponseDto = OrderProductAllListResponseDto.getOrderProductListResponseDto(page);

        //given
        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .httpStatus(HttpStatus.OK)
                .status(ResponseStatus.SUCCESS.getDescription())
                .message(SuccessCode.ORDER_PRODUCT_ALL_INQUIRY_SUCCESS.getDescription())
                .data(orderProductAllListResponseDto)
                .build();

        //when
        given(orderProductAllInquiryService.orderProductAllListResponse(anyString(), anyInt(), anyInt(), anyString()))
                .willReturn(commonResponse);

        //then
        mvc.perform(get("/api/v1/user/order")
                        .with(csrf())
                        .header("Authorization", bearerAccessToken)
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ResponseStatus.SUCCESS.getDescription()))
                .andExpect(jsonPath("$.message").value(SuccessCode.ORDER_PRODUCT_ALL_INQUIRY_SUCCESS.getDescription()))
                .andExpect(jsonPath("$.data.orderProductAllList[0].orderName").value(order.getOrderName()))
                .andExpect(jsonPath("$.data.orderProductAllList[0].productName").value(product.getName()))
                .andExpect(jsonPath("$.data.orderProductAllList[0].orderProductCount").value(order.getCount()))
                .andExpect(jsonPath("$.data.orderProductAllList[0].totalOrderPrice").value(order.getTotalPrice()))
                .andExpect(jsonPath("$.data.orderProductAllList[0].productBrand").value(brand.getName()))
                .andExpect(jsonPath("$.data.pagination.totalPages").value(1))
                .andExpect(jsonPath("$.data.pagination.totalElements").value(1))
                .andExpect(jsonPath("$.data.pagination.pageNo").value(0))
                .andExpect(jsonPath("$.data.pagination.lastPage").value(true))
                .andDo(print());
    }

}