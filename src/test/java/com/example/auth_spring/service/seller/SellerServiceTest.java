package com.example.auth_spring.service.seller;

import com.example.auth_spring.security.jwt.service.TokenService;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.type.Role;
import com.example.auth_spring.web.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class SellerServiceTest {

    private SellerService sellerService;
    private TokenService tokenService;
    private CommonService commonService;
    private User user;

    @BeforeEach
    void setup() {
        tokenService = mock(TokenService.class);
        sellerService = new SellerService(commonService, tokenService);
        user = new User();
        ReflectionTestUtils.setField(user, "id", 1L);
    }


    @Test
    @DisplayName("[Service] 판매자 전환 성공")
    void sellerConversionSuccess() {
        //given
        String bearerAccessToken = "Bearer accessToken";
        given(tokenService.findUser(bearerAccessToken)).willReturn(user);

        //when
        Long userId = sellerService.conversion(bearerAccessToken);

        //then
        assertThat(userId).isEqualTo(user.getId());
        assertThat(user.getRoleKey()).isEqualTo(Role.SELLER.getKey());
    }
}