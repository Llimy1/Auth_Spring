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

import java.util.Random;

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

        String orderName;

        // do-while 사용하여 true면 중복으로 판단하여 orderName 다시 생성 false면 루프 탈출
        do {
            orderName = createName();
        } while (isOrderNameDuplicate(orderName));

        User user = tokenService.findUser(bearerAccessToken);
        Product product = productRepository.findByName(productName)
                .orElseThrow(() -> new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND));
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ADDRESS_NOT_FOUND));

        Order order = orderProductRequestDto.toOrderEntity(user, product, address, orderName);


        orderRepository.save(order);
    }

    // API 반환
    @Transactional
    public CommonResponse<Object> orderProductRegistrationResponse(String bearerAccessToken, OrderProductRequestDto orderProductRequestDto) {
        orderProductRegistration(bearerAccessToken, orderProductRequestDto);

        return commonService.successResponse(SuccessCode.ORDER_PRODUCT_REGISTRATION_SUCCESS.getDescription(), HttpStatus.CREATED, null);
    }

    // 주문 번호 중복 확인 (중복이면 true, 아니면 false)
    private boolean isOrderNameDuplicate(String orderName) {
        return orderRepository.existsByOrderName(orderName);
    }

    // 8자리 이름 무작위 생성
    private String createName() {
        StringBuilder name = new StringBuilder();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) {
            int index = rnd.nextInt(3);

            switch (index) {
                case 0:
                    // 소문자 무작위
                    name.append((char) (rnd.nextInt(26)) + 97);
                    break;
                case 1:
                    // 대문자 무작위
                    name.append((char) (rnd.nextInt(26)) + 65);
                    break;
                case 2:
                    // 숫자 무작위
                    name.append((rnd.nextInt(10)));
                    break;
            }
        }
        return name.toString();
    }
}
