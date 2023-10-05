package com.example.auth_spring.service.user.order.Inquiry;

import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.service.user.token.TokenService;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.domain.order.OrderRepository;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.order.OrderProductDetailResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderProductDetailInquiryService {

    private final TokenService tokenService;
    private final CommonService commonService;
    private final OrderRepository orderRepository;


    // 주문 상세 정보 조회
    public OrderProductDetailResponseDto orderProductDetail(String bearerAccessToken, String orderName) {
        tokenService.accessTokenExpiration(bearerAccessToken);

        return orderRepository.findDetailOrder(orderName);
    }

    // API 반환
    public CommonResponse<Object> orderProductDetailResponse(String bearerAccessToken, String orderName) {
        return commonService.successResponse(SuccessCode.ORDER_PRODUCT_DETAIL_INQUIRY_SUCCESS.getDescription(), HttpStatus.OK, orderProductDetail(bearerAccessToken, orderName));
    }
}
