package com.example.auth_spring.service.user.order.Inquiry;

import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.service.user.token.TokenService;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.domain.order.OrderRepository;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.order.OrderProductAllListResponseDto;
import com.example.auth_spring.web.dto.order.OrderProductAllResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderProductAllInquiryService {

    private final TokenService tokenService;
    private final CommonService commonService;
    private final OrderRepository orderRepository;


    // 주문 전체 조회
    public OrderProductAllListResponseDto orderProductAllList(String bearerAccessToken, int page, int size, String sortBy) {
        tokenService.accessTokenExpiration(bearerAccessToken);

        String email = tokenService.accessTokenEmail(bearerAccessToken);

        PageRequest pageable = PageRequest.of(page - 1, size, Sort.by(sortBy).descending());

        Page<OrderProductAllResponseDto> data = orderRepository.findAllOrderList(email, pageable);

        return OrderProductAllListResponseDto.getOrderProductListResponseDto(data);
    }

    // API 반환
    public CommonResponse<Object> orderProductAllListResponse(String bearerAccessToken, int page, int size, String sortBy) {

        return commonService.successResponse(SuccessCode.ORDER_PRODUCT_ALL_INQUIRY_SUCCESS.getDescription(), HttpStatus.OK, orderProductAllList(bearerAccessToken, page, size, sortBy));

    }
}
