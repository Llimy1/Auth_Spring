package com.example.auth_spring.service.user.order.registration;

import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.service.user.token.TokenService;
import com.example.auth_spring.type.ErrorCode;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.domain.address.Address;
import com.example.auth_spring.web.domain.address.AddressRepository;
import com.example.auth_spring.web.domain.order.Order;
import com.example.auth_spring.web.domain.order.OrderRepository;
import com.example.auth_spring.web.domain.product.Product;
import com.example.auth_spring.web.domain.product.ProductRepository;
import com.example.auth_spring.web.domain.user.User;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.order.OrderProductRequestDto;
import com.example.auth_spring.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OrderProductRegistrationService {

    private final TokenService tokenService;
    private final CommonService commonService;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final AddressRepository addressRepository;

    // 주문 정보 저장
    @Transactional
    public void orderProductRegistration(String bearerAccessToken, OrderProductRequestDto orderProductRequestDto) {
        tokenService.accessTokenExpiration(bearerAccessToken);

        String productName = orderProductRequestDto.getProductName();
        Long addressId = orderProductRequestDto.getAddressId();

        User user = tokenService.findUser(bearerAccessToken);
        Product product = productRepository.findByName(productName)
                .orElseThrow(() -> new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND));
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ADDRESS_NOT_FOUND));

        Order order = orderProductRequestDto.toOrderEntity(user, product, address);


        orderRepository.save(order);
    }


    // API 반환
    @Transactional
    public CommonResponse<Object> orderProductRegistrationResponse(String bearerAccessToken, OrderProductRequestDto orderProductRequestDto) {
        orderProductRegistration(bearerAccessToken, orderProductRequestDto);

        return commonService.successResponse(SuccessCode.ORDER_PRODUCT_REGISTRATION_SUCCESS.getDescription(), HttpStatus.CREATED, null);
    }
}
