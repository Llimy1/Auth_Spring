package com.example.auth_spring.web.controller.auth.withdrawal;

import com.example.auth_spring.security.jwt.filter.JwtAuthFilter;
import com.example.auth_spring.service.auth.logout.BasicLogoutService;
import com.example.auth_spring.service.auth.withdrawal.WithdrawalService;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.type.ResponseStatus;
import com.example.auth_spring.type.SuccessCode;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WithdrawalController.class)
class WithdrawalControllerTest {
    @MockBean
    private WithdrawalService withdrawalService;

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
    @DisplayName("[API] 회원탈퇴 성공")
    @WithMockUser(roles = "USER")
    void loginSuccess() throws Exception {

        String accessToken = "accessToken";

        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .httpStatus(HttpStatus.OK)
                .status(ResponseStatus.SUCCESS.getDescription())
                .message(SuccessCode.WITHDRAWAL_SUCCESS.getDescription())
                .data(null)
                .build();

        //given
        given(withdrawalService.withdrawalResponse(any()))
                .willReturn(commonResponse);

        //when
        //then
        mvc.perform(delete("/api/v1/user/withdrawal")
                        .with(csrf())
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ResponseStatus.SUCCESS.getDescription()))
                .andExpect(jsonPath("$.message").value(SuccessCode.WITHDRAWAL_SUCCESS.getDescription()))
                .andDo(print());
    }

}