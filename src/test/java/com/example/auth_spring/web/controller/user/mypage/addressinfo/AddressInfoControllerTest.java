package com.example.auth_spring.web.controller.user.mypage.addressinfo;

import com.example.auth_spring.security.jwt.service.JwtProvider;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.service.user.mypage.addressinfo.AddressInfoService;
import com.example.auth_spring.type.ResponseStatus;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.controller.user.mypage.addressinfo.AddressInfoController;
import com.example.auth_spring.web.domain.address.Address;
import com.example.auth_spring.web.domain.user.User;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.mypage.addressInfo.AddressInfoListResponseDto;
import com.example.auth_spring.web.dto.mypage.addressInfo.AddressInfoResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AddressInfoController.class)
class AddressInfoControllerTest {

    @MockBean
    private AddressInfoService addressInfoService;

    @MockBean
    private CommonService commonService;

    @MockBean
    private JwtProvider jwtProvider;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("[API] 내 주소 정보 조회 성공")
    @WithMockUser(roles = "USER")
    void addressInfoCheckSuccess() throws Exception {

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

        String bearerAccessToken = "Bearer accessToken";

        AddressInfoResponseDto addressInfoResponseDto = AddressInfoResponseDto.builder()
                .zipCode(address.getZipCode())
                .streetAddress(address.getStreetAddress())
                .detailAddress(address.getDetailAddress())
                .build();

        List<AddressInfoResponseDto> addressInfoResponseDtoList = new ArrayList<>(Collections.singletonList(addressInfoResponseDto));


        //given


        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .httpStatus(HttpStatus.OK)
                .status(ResponseStatus.SUCCESS.getDescription())
                .message(SuccessCode.ADDRESS_INFO_CHECK_SUCCESS.getDescription())
                .data(new AddressInfoListResponseDto(addressInfoResponseDtoList))
                .build();


        given(addressInfoService.addressInfoResponse(any()))
                .willReturn(commonResponse);

        //when
        //then
        mvc.perform(get("/api/v1/user/addressInfo")
                        .with(csrf())
                        .header("Authorization", bearerAccessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ResponseStatus.SUCCESS.getDescription()))
                .andExpect(jsonPath("$.message").value(SuccessCode.ADDRESS_INFO_CHECK_SUCCESS.getDescription()))
                .andExpect(jsonPath("$.data.addressInfoResponseDtoList[0].zipCode").value("001011"))
                .andExpect(jsonPath("$.data.addressInfoResponseDtoList[0].streetAddress").value("서울"))
                .andExpect(jsonPath("$.data.addressInfoResponseDtoList[0].detailAddress").value("도곡 10"))
                .andDo(print());
    }
}