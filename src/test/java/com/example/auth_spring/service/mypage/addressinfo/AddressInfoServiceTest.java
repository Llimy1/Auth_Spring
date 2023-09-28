package com.example.auth_spring.service.mypage.addressinfo;

import com.example.auth_spring.security.jwt.service.TokenService;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.web.domain.address.Address;
import com.example.auth_spring.web.domain.user.User;
import com.example.auth_spring.web.dto.mypage.addressInfo.AddressInfoResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class AddressInfoServiceTest {

    private TokenService tokenService;
    private CommonService commonService;
    private AddressInfoService addressInfoService;
    private Address address;

    @BeforeEach
    void setup() {
        tokenService = mock(TokenService.class);
        addressInfoService = new AddressInfoService(tokenService, commonService);

        User user = User.builder()
                .email("abcd@naver.com")
                .name("홍길동")
                .nickname("바람")
                .phoneNumber("01000000000")
                .gender("male")
                .introduce("안녕하세요")
                .profileImgUrl("https://img_url")
                .build();

        ReflectionTestUtils.setField(user, "id", 1L);

        address = Address.builder()
                .user(user)
                .zipCode("001011")
                .streetAddress("서울")
                .detailAddress("도곡 10")
                .isDefault(true)
                .build();
    }

    @Test
    @DisplayName("[Service] 내 주소 정보 조회 성공")
    void addressInfoCheckSuccess() {
        String bearerAccessToken = "Bearer accessToken";

        //given
        given(tokenService.findAddress(bearerAccessToken)).willReturn(address);

        //when
        AddressInfoResponseDto addressInfoResponseDto = addressInfoService.addressInfo(bearerAccessToken);

        //then
        assertThat(addressInfoResponseDto.getStreetAddress()).isEqualTo(address.getStreetAddress());
    }

}