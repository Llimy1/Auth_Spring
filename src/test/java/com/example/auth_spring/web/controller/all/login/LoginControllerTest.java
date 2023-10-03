package com.example.auth_spring.web.controller.all.login;

import com.example.auth_spring.security.jwt.filter.JwtAuthFilter;
import com.example.auth_spring.service.all.login.BasicLoginService;
import com.example.auth_spring.service.all.login.OAuth2LoginService;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.type.ErrorCode;
import com.example.auth_spring.type.ResponseStatus;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.controller.all.login.LoginController;
import com.example.auth_spring.web.dto.auth.login.BasicLoginRequestDto;
import com.example.auth_spring.web.dto.common.CommonResponse;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(LoginController.class)
class LoginControllerTest {

    @MockBean
    private BasicLoginService basicLoginService;

    @MockBean
    private OAuth2LoginService oAuth2LoginService;

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
    @DisplayName("[API] 로그인 성공")
    @WithMockUser(roles = "USER")
    void loginSuccess() throws Exception {

        String body = objectMapper.writeValueAsString(loginRequestDto());

        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .httpStatus(HttpStatus.CREATED)
                .status(ResponseStatus.SUCCESS.getDescription())
                .message(SuccessCode.BASIC_LOGIN_SUCCESS.getDescription())
                .data(null)
                .build();
        //given

        given(basicLoginService.basicLoginResponse(any(), any()))
                .willReturn(commonResponse);

        //when
        //then
        mvc.perform(post("/api/v1/all/login/basic")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(ResponseStatus.SUCCESS.getDescription()))
                .andExpect(jsonPath("$.message").value(SuccessCode.BASIC_LOGIN_SUCCESS.getDescription()))
                .andDo(print());
    }

    @Test
    @DisplayName("[API] 로그인 실패")
    @WithMockUser(roles = "USER")
    void loginFail() throws Exception {

        String body = objectMapper.writeValueAsString(loginRequestDto());

        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .status(ResponseStatus.FAIL.getDescription())
                .message(ErrorCode.LOGIN_EXCEPTION.getDescription())
                .data(null)
                .build();


        //given
        given(basicLoginService.basicLoginResponse(any(), any()))
                .willReturn(commonResponse);

        //when
        //then
        mvc.perform(post("/api/v1/all/login/basic")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(ResponseStatus.FAIL.getDescription()))
                .andExpect(jsonPath("$.message").value(ErrorCode.LOGIN_EXCEPTION.getDescription()))
                .andDo(print());
    }

    private BasicLoginRequestDto loginRequestDto() {
        String email = "abcd@naver.com";
        String password = "1234";

        return BasicLoginRequestDto.builder()
                .email(email)
                .password(password)
                .build();
    }

}