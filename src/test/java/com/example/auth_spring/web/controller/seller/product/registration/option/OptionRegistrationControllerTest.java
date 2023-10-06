package com.example.auth_spring.web.controller.seller.product.registration.option;

import com.example.auth_spring.security.jwt.service.JwtProvider;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.service.seller.registration.option.OptionRegistrationService;
import com.example.auth_spring.service.user.token.TokenService;
import com.example.auth_spring.type.ResponseStatus;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.option.OptionRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OptionRegistrationController.class)
class OptionRegistrationControllerTest {

    @MockBean
    private OptionRegistrationService optionRegistrationService;

    @MockBean
    private CommonService commonService;

    @MockBean
    private JwtProvider jwtProvider;

    @MockBean
    private TokenService tokenService;

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

    private OptionRequestDto optionRequestDto() {
        return OptionRequestDto.builder()
                .optionName("XL")
                .build();
    }

    @Test
    @DisplayName("[API] 옵션 저장 성공")
    @WithMockUser(roles = "SELLER")
    void optionRegistrationSuccess() throws Exception {

        String body = objectMapper.writeValueAsString(optionRequestDto());

        String bearerAccessToken = "Bearer accessToken";

        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .httpStatus(HttpStatus.CREATED)
                .status(ResponseStatus.SUCCESS.getDescription())
                .message(SuccessCode.OPTION_REGISTRATION_SUCCESS.getDescription())
                .data(null)
                .build();

        //given
        given(optionRegistrationService.optionRegistrationResponse(anyString(), any()))
                .willReturn(commonResponse);

        //when
        //then
        mvc.perform(post("/api/v1/seller/option/registration")
                        .with(csrf())
                        .header("Authorization", bearerAccessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(ResponseStatus.SUCCESS.getDescription()))
                .andExpect(jsonPath("$.message").value(SuccessCode.OPTION_REGISTRATION_SUCCESS.getDescription()))
                .andDo(print());
    }

}