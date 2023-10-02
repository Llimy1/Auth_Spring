package com.example.auth_spring.web.controller.subcategory.update;

import com.example.auth_spring.security.jwt.service.JwtProvider;
import com.example.auth_spring.security.jwt.service.TokenService;
import com.example.auth_spring.service.category.update.CategoryUpdateService;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.service.subcategory.update.SubCategoryUpdateService;
import com.example.auth_spring.type.ResponseStatus;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.dto.category.CategoryUpdateResponseDto;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.subcategory.SubCategoryUpdateResponseDto;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SubCategoryUpdateController.class)
class SubCategoryUpdateControllerTest {
    @MockBean
    private SubCategoryUpdateService subCategoryUpdateService;

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
    @DisplayName("[API] 서브 카테고리명 수정 성공")
    @WithMockUser(roles = "ADMIN")
    void subCategoryUpdateSuccess() throws Exception {

        String bearerAccessToken = "Bearer accessToken";
        String beforeSubCategoryName = "맨투맨";
        String afterSubCategoryName = "후드티";

        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .httpStatus(HttpStatus.OK)
                .status(ResponseStatus.SUCCESS.getDescription())
                .message(SuccessCode.SUB_CATEGORY_UPDATE_SUCCESS.getDescription())
                .data(SubCategoryUpdateResponseDto.builder()
                        .beforeSubCategoryName(beforeSubCategoryName)
                        .afterSubCategoryName(afterSubCategoryName)
                        .build())
                .build();


        //given
        given(subCategoryUpdateService.subCategoryUpdateResponse(anyString(), anyString(), anyString()))
                .willReturn(commonResponse);

        //when
        //then
        mvc.perform(patch("/api/v1/admin/subCategory/{beforeSubCategoryName}/{afterSubCategoryName}", "맨투맨", "후드티")
                        .with(csrf())
                        .header("Authorization", bearerAccessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ResponseStatus.SUCCESS.getDescription()))
                .andExpect(jsonPath("$.message").value(SuccessCode.SUB_CATEGORY_UPDATE_SUCCESS.getDescription()))
                .andExpect(jsonPath("$.data.beforeSubCategoryName").value(beforeSubCategoryName))
                .andExpect(jsonPath("$.data.afterSubCategoryName").value(afterSubCategoryName))
                .andDo(print());
    }
}