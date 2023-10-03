package com.example.auth_spring.web.controller.admin.subcategory.registration;

import com.example.auth_spring.security.jwt.service.JwtProvider;
import com.example.auth_spring.service.user.token.TokenService;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.service.admin.subcategory.registration.SubCategoryRegistrationService;
import com.example.auth_spring.type.ResponseStatus;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.controller.admin.subcategory.registration.SubCategoryRegistrationController;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.subcategory.SubCategoryRequestDto;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SubCategoryRegistrationController.class)
class SubCategoryRegistrationControllerTest {
    @MockBean
    private SubCategoryRegistrationService subCategoryRegistrationService;

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

    private SubCategoryRequestDto subCategoryRequestDto() {
        return SubCategoryRequestDto.builder()
                .subCategoryName("맨투맨")
                .build();
    }

    @Test
    @DisplayName("[API] 서브 카테고리 등록 성공")
    @WithMockUser(roles = "ADMIN")
    void subCategoryRegistrationSuccess() throws Exception {

        String body = objectMapper.writeValueAsString(subCategoryRequestDto());

        String bearerAccessToken = "Bearer accessToken";

        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .httpStatus(HttpStatus.CREATED)
                .status(ResponseStatus.SUCCESS.getDescription())
                .message(SuccessCode.SUB_CATEGORY_REGISTRATION_SUCCESS.getDescription())
                .data(null)
                .build();

        //given
        given(subCategoryRegistrationService.subCategoryRegistrationResponse(anyString(), anyString(), any()))
                .willReturn(commonResponse);

        //when
        //then
        mvc.perform(post("/api/v1/admin/subCategory/{categoryName}/registration", "의류")
                .with(csrf())
                .header("Authorization", bearerAccessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(ResponseStatus.SUCCESS.getDescription()))
                .andExpect(jsonPath("$.message").value(SuccessCode.SUB_CATEGORY_REGISTRATION_SUCCESS.getDescription()))
                .andDo(print());
    }
}