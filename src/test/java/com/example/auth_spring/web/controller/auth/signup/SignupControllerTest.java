package com.example.auth_spring.web.controller.auth.signup;

import com.example.auth_spring.security.jwt.filter.JwtAuthFilter;
import com.example.auth_spring.service.auth.signup.BasicSignupService;
import com.example.auth_spring.service.auth.signup.OAuth2SignupService;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.type.ErrorCode;
import com.example.auth_spring.type.ResponseStatus;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.auth.signup.BasicSignupRequestDto;
import com.example.auth_spring.web.dto.auth.signup.UserIdResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SignupController.class)
class SignupControllerTest {

    @MockBean
    private BasicSignupService basicSignupService;

    @MockBean
    private OAuth2SignupService oAuth2SignupService;

    @MockBean
    private CommonService commonService;

    @MockBean
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }


    @Test
    @DisplayName("[API] 회원 가입 성공")
    @WithMockUser(roles = "USER")
    void signupSuccess() throws Exception {

        String body = objectMapper.writeValueAsString(signupRequestDto());

        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .httpStatus(HttpStatus.CREATED)
                .status(ResponseStatus.SUCCESS.getDescription())
                .message(SuccessCode.BASIC_SIGNUP_SUCCESS.getDescription())
                .data(new UserIdResponseDto(1L))
                .build();

        //given
        given(basicSignupService.signupResponse(any()))
                .willReturn(commonResponse);


        //when
        //then
        mvc.perform(post("/api/v1/all/signup/basic")
                        .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(ResponseStatus.SUCCESS.getDescription()))
                .andExpect(jsonPath("$.message").value(SuccessCode.BASIC_SIGNUP_SUCCESS.getDescription()))
                .andExpect(jsonPath("$.data.userId").value(1))
                .andDo(print());
    }

    @Test
    @DisplayName("[API] 회원 가입 실패 - 닉네임 중복")
    @WithMockUser(roles = "USER")
    void signFail() throws Exception {
        String body = objectMapper.writeValueAsString(signupRequestDto());

        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .status(ResponseStatus.FAIL.getDescription())
                .message(ErrorCode.NICKNAME_THAT_EXIST.getDescription())
                .data(null)
                .build();

        //given
        given(basicSignupService.signupResponse(any()))
                .willReturn(commonResponse);


        //when
        //then
        mvc.perform(post("/api/v1/all/signup/basic")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(ResponseStatus.FAIL.getDescription()))
                .andExpect(jsonPath("$.message").value(ErrorCode.NICKNAME_THAT_EXIST.getDescription()))
                .andDo(print());
    }

    private BasicSignupRequestDto signupRequestDto() {
        String email = "abce@naver.com";
        String password = "1234";
        String name = "홍길동";
        String nickname = "바람";
        String phoneNumber = "01000000000";
        String gender = "male";
        String introduce = "안녕하세요 홍길동 입니다.";
        String profileImgUrl = "https://img_url";

        String zipCode = "12345";
        String streetAddress = "서울시 강남구";
        String detailAddress = "1길 30";


        return BasicSignupRequestDto.builder()
                .email(email)
                .password(password)
                .name(name)
                .nickname(nickname)
                .phoneNumber(phoneNumber)
                .gender(gender)
                .introduce(introduce)
                .profileImgUrl(profileImgUrl)
                .zipCode(zipCode)
                .streetAddress(streetAddress)
                .detailAddress(detailAddress)
                .build();
    }

}