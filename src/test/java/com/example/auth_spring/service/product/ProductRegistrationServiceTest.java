package com.example.auth_spring.service.product;

import com.example.auth_spring.security.jwt.service.JwtProvider;
import com.example.auth_spring.security.jwt.service.TokenService;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.type.Role;
import com.example.auth_spring.web.domain.product.Product;
import com.example.auth_spring.web.domain.product.ProductRepository;
import com.example.auth_spring.web.domain.user.User;
import com.example.auth_spring.web.domain.user.UserRepository;
import com.example.auth_spring.web.dto.product.ProductRequestDto;
import com.example.auth_spring.web.exception.IllegalStateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductRegistrationServiceTest {



    private TokenService tokenService;
    private CommonService commonService;

    private ProductRepository productRepository;

    private ProductRegistrationService productRegistrationService;

    @BeforeEach
    void setup() {
        tokenService = mock(TokenService.class);
        productRepository = mock(ProductRepository.class);
        productRegistrationService = new ProductRegistrationService(tokenService, commonService, productRepository);
    }

    private ProductRequestDto productRequestDto() {
        return ProductRequestDto.builder()
                .productName("옷")
                .productPrice(10000L)
                .build();
    }


    @Test
    @DisplayName("[Service] 상품 등록 성공")
    void productRegistrationSuccess() {

        //given
        given(tokenService.findUserRole(anyString())).willReturn(Role.SELLER.getKey());


        ProductRequestDto productRequestDto = productRequestDto();
        Product product = productRequestDto.toProductEntity();

        ReflectionTestUtils.setField(product, "id", 1L);

        given(productRepository.save(any()))
                .willReturn(product);

        String bearerAccessToken = "Bearer accessToken";

        //when
        Long productId = productRegistrationService.registration(bearerAccessToken, productRequestDto);

        //then
        assertThat(productId).isEqualTo(product.getId());

    }

    @Test
    @DisplayName("[Service] 상품 등록 실패 - 판매자 권한 없음")
    void productRegistrationFail() {
        String bearerAccessToken = "Bearer accessToken";

        //given
        given(tokenService.findUserRole(anyString())).willReturn(Role.USER.getKey());

        //when
        //then
        assertThatThrownBy(() -> productRegistrationService.registration(bearerAccessToken, productRequestDto()))
                .isInstanceOf(IllegalStateException.class);
    }
}