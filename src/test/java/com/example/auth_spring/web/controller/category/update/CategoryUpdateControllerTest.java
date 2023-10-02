package com.example.auth_spring.web.controller.category.update;

import com.example.auth_spring.security.jwt.service.JwtProvider;
import com.example.auth_spring.security.jwt.service.TokenService;
import com.example.auth_spring.service.category.delete.CategoryDeleteService;
import com.example.auth_spring.service.category.update.CategoryUpdateService;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.type.ResponseStatus;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.dto.category.CategoryUpdateResponseDto;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryUpdateController.class)
class CategoryUpdateControllerTest {

    @MockBean
    private CategoryUpdateService categoryUpdateService;

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

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("[API] 카테고리명 수정 성공")
    @WithMockUser(roles = "ADMIN")
    void categoryUpdateSuccess() throws Exception {

        String bearerAccessToken = "Bearer accessToken";
        String beforeCategoryName = "의류";
        String afterCategoryName = "잡화";

        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .httpStatus(HttpStatus.OK)
                .status(ResponseStatus.SUCCESS.getDescription())
                .message(SuccessCode.CATEGORY_UPDATE_SUCCESS.getDescription())
                .data(CategoryUpdateResponseDto.builder()
                        .beforeCategoryName(beforeCategoryName)
                        .afterCategoryName(afterCategoryName)
                        .build())
                .build();

        //given
        given(categoryUpdateService.categoryUpdateResponse(anyString(), anyString(), anyString()))
                .willReturn(commonResponse);

        //when
        //then
        mvc.perform(patch("/api/v1/admin/category/{beforeCategoryName}/{afterCategoryName}", "의류", "잡화")
                .with(csrf())
                        .header("Authorization", bearerAccessToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ResponseStatus.SUCCESS.getDescription()))
                .andExpect(jsonPath("$.message").value(SuccessCode.CATEGORY_UPDATE_SUCCESS.getDescription()))
                .andExpect(jsonPath("$.data.beforeCategoryName").value(beforeCategoryName))
                .andExpect(jsonPath("$.data.afterCategoryName").value(afterCategoryName))
                .andDo(print());
    }
}