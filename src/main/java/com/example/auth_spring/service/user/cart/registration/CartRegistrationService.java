package com.example.auth_spring.service.user.cart.registration;

import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.service.user.token.TokenService;
import com.example.auth_spring.type.ErrorCode;
import com.example.auth_spring.type.Role;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.domain.cart.Cart;
import com.example.auth_spring.web.domain.cart.CartRepository;
import com.example.auth_spring.web.domain.productoption.ProductOption;
import com.example.auth_spring.web.domain.productoption.ProductOptionRepository;
import com.example.auth_spring.web.domain.user.User;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.exception.IllegalStateException;
import com.example.auth_spring.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CartRegistrationService {

    private final CartRepository cartRepository;
    private final ProductOptionRepository productOptionRepository;
    private final TokenService tokenService;
    private final CommonService commonService;


    // 장바구니 상품 추가
    @Transactional
    public void cartRegistration(String bearerAccessToken, String productName, Integer productCount) {
        tokenService.accessTokenExpiration(bearerAccessToken);

        User user = tokenService.findUser(bearerAccessToken);

        if (!user.getRole().equals(Role.USER)) {
            throw new IllegalStateException(ErrorCode.AUTHORITY_NOT_USER);
        }

        cartRepository.findProductName(productName).ifPresent(a -> {
            throw new IllegalStateException(ErrorCode.PRODUCT_THAT_EXIST);
        });

        ProductOption productOption = productOptionRepository.findProductOptionByProductName(productName)
                .orElseThrow(() -> new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND));

        if (productCount > productOption.getStock()) {
            throw new IllegalStateException(ErrorCode.PRODUCT_STOCK_OVER_REGISTRATION);
        }


        Cart cart = Cart.builder()
                .user(user)
                .productOption(productOption)
                .count(productCount)
                .build();


        cartRepository.save(cart);
    }

    // API 반환
    @Transactional
    public CommonResponse<Object> cartRegistrationResponse(String bearerAccessToken, String productName, Integer productCount) {
        cartRegistration(bearerAccessToken, productName, productCount);
        return commonService.successResponse(SuccessCode.CART_REGISTRATION_SUCCESS.getDescription(), HttpStatus.CREATED, null);
    }
}
