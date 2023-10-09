package com.example.auth_spring.web.controller.admin.subcategory.Inquiry;

import com.example.auth_spring.security.jwt.service.JwtProvider;
import com.example.auth_spring.service.user.token.TokenService;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.service.admin.subcategory.Inquiry.SubCategoryInquiryService;
import com.example.auth_spring.type.ResponseStatus;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.controller.admin.subcategory.Inquiry.SubCategoryInquiryController;
import com.example.auth_spring.web.domain.category.Category;
import com.example.auth_spring.web.domain.subcategory.SubCategory;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.subcategory.SubCategoryListResponseDto;
import com.example.auth_spring.web.dto.subcategory.SubCategoryResponseDto;
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

@WebMvcTest(SubCategoryInquiryController.class)
class SubCategoryInquiryControllerTest {

    @MockBean
    private SubCategoryInquiryService subCategoryInquiryService;

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
    @DisplayName("[API] 서브 카테고리 조회 성공")
    @WithMockUser(roles = "ADMIN")
    void subCategoryCheckSuccess() throws Exception {

        String bearerAccessToken = "Bearer accessToken";

        Category category = Category.builder()
                .name("의류")
                .build();

        ReflectionTestUtils.setField(category, "id", 1L);

        SubCategory subCategory = SubCategory.builder()
                .category(category)
                .name("맨투맨")
                .build();

        SubCategoryResponseDto subCategoryResponseDto = SubCategoryResponseDto.builder()
                .subCategoryName(subCategory.getName())
                .build();

        List<SubCategoryResponseDto> subCategoryResponseDtoList = new ArrayList<>(Collections.singleton(subCategoryResponseDto));

        SubCategoryListResponseDto subCategoryListResponseDto  = SubCategoryListResponseDto.builder()
                .categoryName(category.getName())
                .subCategoryNameList(subCategoryResponseDtoList)
                .build();

        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .httpStatus(HttpStatus.OK)
                .status(ResponseStatus.SUCCESS.getDescription())
                .message(SuccessCode.SUB_CATEGORY_INQUIRY_SUCCESS.getDescription())
                .data(subCategoryListResponseDto)
                .build();

        //given
        given(subCategoryInquiryService.subCategoryListResponse(any(), any()))
                .willReturn(commonResponse);

        //when
        //then
        mvc.perform(get("/api/v1/admin/subCategory/{categoryName}", "의류")
                .with(csrf())
                .header("Authorization", bearerAccessToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ResponseStatus.SUCCESS.getDescription()))
                .andExpect(jsonPath("$.message").value(SuccessCode.SUB_CATEGORY_INQUIRY_SUCCESS.getDescription()))
                .andExpect(jsonPath("$.data.categoryName").value("의류"))
                .andExpect(jsonPath("$.data.subCategoryNameList[0].subCategoryName").value("맨투맨"))
                .andDo(print());
    }
}