package com.example.auth_spring.service.product;

import com.example.auth_spring.security.jwt.service.TokenService;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.type.ErrorCode;
import com.example.auth_spring.type.Role;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.domain.product.Product;
import com.example.auth_spring.web.domain.product.ProductRepository;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.product.ProductIdResponseDto;
import com.example.auth_spring.web.dto.product.ProductRequestDto;
import com.example.auth_spring.web.exception.IllegalStateException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProductRegistrationService {

    private final TokenService tokenService;
    private final CommonService commonService;
    private final ProductRepository productRepository;


    @Transactional
    public Long registration(String bearerAccessToken, ProductRequestDto productRequestDto) {
        tokenService.accessTokenExpiration(bearerAccessToken);

        String userRole = tokenService.findUserRole(bearerAccessToken);

        if (!userRole.equals(Role.SELLER.getKey())) {
            throw new IllegalStateException(ErrorCode.AUTHORITY_NOT_SELLER);
        }

        Product product = productRequestDto.toProductEntity();

        return productRepository.save(product).getId();
    }

    @Transactional
    public CommonResponse<Object> registrationResponse(String bearerAccessToken, ProductRequestDto productRequestDto) {
        Long productId = registration(bearerAccessToken, productRequestDto);

        return commonService.successResponse(SuccessCode.PRODUCT_REGISTRATION_SUCCESS.getDescription(), HttpStatus.CREATED, new ProductIdResponseDto(productId));
    }


}
