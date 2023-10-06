package com.example.auth_spring.service.user.cart.delete;

import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.service.user.token.TokenService;
import com.example.auth_spring.type.ErrorCode;
import com.example.auth_spring.type.Role;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.domain.cart.Cart;
import com.example.auth_spring.web.domain.cart.CartRepository;
import com.example.auth_spring.web.domain.product.ProductRepository;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CartDeleteService {

    private final TokenService tokenService;
    private final CommonService commonService;
    private final CartRepository cartRepository;


    // 장바구니 삭제
    @Transactional
    public void cartDelete(String bearerAccessToken, String productName) {
        tokenService.accessTokenExpiration(bearerAccessToken);

        if (!tokenService.findUserRole(bearerAccessToken).equals(Role.USER.getKey())) {
            throw new NotFoundException(ErrorCode.USER_NOT_FOUND);
        }

        Cart cart = cartRepository.findProductName(productName)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CART_PRODUCT_NOT_FOUND));

        cartRepository.delete(cart);
    }


    // API 반환
    @Transactional
    public CommonResponse<Object> cartDeleteResponse(String bearerAccessToken, String productName) {
        cartDelete(bearerAccessToken, productName);
        return commonService.successResponse(SuccessCode.CART_DELETE_SUCCESS.getDescription(), HttpStatus.OK, null);
    }
}
