package com.example.auth_spring.service.mypage.myinfo;

import com.example.auth_spring.security.jwt.service.TokenService;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.web.domain.address.Address;
import com.example.auth_spring.web.domain.user.User;
import com.example.auth_spring.web.dto.mypage.myinfo.MyInfoResponseDto;
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
class MyInfoServiceTest {

    private MyInfoService myInfoService;
    private TokenService tokenService;
    private CommonService commonService;
    private User user;

    @BeforeEach
    void setup() {
        tokenService = mock(TokenService.class);
        myInfoService = new MyInfoService(tokenService, commonService);
        user = User.builder()
                .email("abcd@naver.com")
                .name("홍길동")
                .nickname("바람")
                .phoneNumber("01000000000")
                .gender("male")
                .introduce("안녕하세요")
                .profileImgUrl("https://img_url")
                .build();

        ReflectionTestUtils.setField(user, "id", 1L);
    }

    @Test
    @DisplayName("[Service] 내 정보 조회 성공")
    void myInfoCheckSuccess() {
        //given
        String bearerAccessToken = "Bearer accessToken";
        given(tokenService.findUser(bearerAccessToken)).willReturn(user);

        //when
        MyInfoResponseDto myInfoResponseDto = myInfoService.myInfo(bearerAccessToken);


        //then
        assertThat(myInfoResponseDto.getName()).isEqualTo(user.getName());

    }

}