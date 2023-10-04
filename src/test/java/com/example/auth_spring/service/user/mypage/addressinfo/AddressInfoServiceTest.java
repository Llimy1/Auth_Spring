package com.example.auth_spring.service.user.mypage.addressinfo;

import com.example.auth_spring.service.user.token.TokenService;
import com.example.auth_spring.service.common.CommonService;

import com.example.auth_spring.web.domain.address.Address;
import com.example.auth_spring.web.domain.address.AddressRepository;
import com.example.auth_spring.web.domain.user.User;
import com.example.auth_spring.web.dto.mypage.addressInfo.AddressInfoListResponseDto;
import com.example.auth_spring.web.dto.mypage.addressInfo.AddressInfoResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class AddressInfoServiceTest {

    private TokenService tokenService;
    private AddressRepository addressRepository;
    private CommonService commonService;
    private AddressInfoService addressInfoService;

    @BeforeEach
    void setup() {
        tokenService = mock(TokenService.class);
        addressRepository = mock(AddressRepository.class);
        addressInfoService = new AddressInfoService(tokenService, commonService, addressRepository);
    }

    @Test
    @DisplayName("[Service] 내 주소 정보 조회 성공")
    void addressInfoCheckSuccess() {
        String bearerAccessToken = "Bearer accessToken";

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

        Address address = Address.builder()
                .user(user)
                .zipCode("001011")
                .streetAddress("서울")
                .detailAddress("도곡 10")
                .isDefault(true)
                .build();

        AddressInfoResponseDto addressInfoResponseDto = AddressInfoResponseDto.builder()
                .zipCode(address.getZipCode())
                .streetAddress(address.getStreetAddress())
                .detailAddress(address.getDetailAddress())
                .build();

        List<AddressInfoResponseDto> addressInfoResponseDtoList = new ArrayList<>(Collections.singletonList(addressInfoResponseDto));


        //given
        given(addressRepository.findAddressListByUserId(any())).willReturn(addressInfoResponseDtoList);

        //when
        AddressInfoListResponseDto addressInfoListResponseDto = addressInfoService.addressInfo(bearerAccessToken);

        //then
        assertThat(addressInfoListResponseDto.getAddressInfoResponseDtoList().get(0).getZipCode()).isEqualTo(address.getZipCode());
    }

}