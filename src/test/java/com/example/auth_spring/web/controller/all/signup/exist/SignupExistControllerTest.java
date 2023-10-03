package com.example.auth_spring.web.controller.all.signup.exist;

import com.example.auth_spring.security.jwt.filter.JwtAuthFilter;
import com.example.auth_spring.service.all.signup.exist.SignupExistService;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.type.ResponseStatus;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.controller.all.signup.exist.SignupExistController;
import com.example.auth_spring.web.dto.common.CommonResponse;
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

@WebMvcTest(SignupExistController.class)
class SignupExistControllerTest {

    @MockBean
    private SignupExistService signupExistService;

    @MockBean
    private CommonService commonService;

    @MockBean
    private JwtAuthFilter jwtAuthFilter;

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
    @DisplayName("[API] 닉네임 사용 가능")
    @WithMockUser(roles = "USER")
    void nicknameExistSuccess() throws Exception {

        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .httpStatus(HttpStatus.OK)
                .status(ResponseStatus.SUCCESS.getDescription())
                .message(SuccessCode.NICKNAME_USE_EXIST.getDescription())
                .data(null)
                .build();

        //given
        given(signupExistService.nicknameExist(any()))
                .willReturn(commonResponse);


        //when
        //then
        mvc.perform(post("/api/v1/all/signup/exist/nickname/{nickname}", "nickname")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ResponseStatus.SUCCESS.getDescription()))
                .andExpect(jsonPath("$.message").value(SuccessCode.NICKNAME_USE_EXIST.getDescription()))
                .andDo(print());
    }

    @Test
    @DisplayName("[API] 이메일 사용 가능")
    @WithMockUser(roles = "USER")
    void emailExistSuccess() throws Exception {

        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .httpStatus(HttpStatus.OK)
                .status(ResponseStatus.SUCCESS.getDescription())
                .message(SuccessCode.EMAIL_USE_EXIST.getDescription())
                .data(null)
                .build();

        //given
        given(signupExistService.emailExist(any()))
                .willReturn(commonResponse);


        //when
        //then
        mvc.perform(post("/api/v1/all/signup/exist/email/{email}", "abcd@naver.com")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ResponseStatus.SUCCESS.getDescription()))
                .andExpect(jsonPath("$.message").value(SuccessCode.EMAIL_USE_EXIST.getDescription()))
                .andDo(print());
    }

    @Test
    @DisplayName("[API] 핸드폰 번호 사용 가능")
    @WithMockUser(roles = "USER")
    void phoneNumberExistSuccess() throws Exception {

        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .httpStatus(HttpStatus.OK)
                .status(ResponseStatus.SUCCESS.getDescription())
                .message(SuccessCode.PHONE_NUMBER_USE_EXIST.getDescription())
                .data(null)
                .build();

        //given
        given(signupExistService.phoneNumberExist(any()))
                .willReturn(commonResponse);


        //when
        //then
        mvc.perform(post("/api/v1/all/signup/exist/phoneNumber/{phoneNumber}", "01000000000")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ResponseStatus.SUCCESS.getDescription()))
                .andExpect(jsonPath("$.message").value(SuccessCode.PHONE_NUMBER_USE_EXIST.getDescription()))
                .andDo(print());
    }
}