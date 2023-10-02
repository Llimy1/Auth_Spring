package com.example.auth_spring.service.category.inquiry;

import com.example.auth_spring.security.jwt.service.TokenService;
import com.example.auth_spring.service.category.registration.CategoryRegistrationService;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.type.Role;
import com.example.auth_spring.web.domain.category.Category;
import com.example.auth_spring.web.domain.category.CategoryRepository;
import com.example.auth_spring.web.dto.category.CategoryListResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class CategoryInquiryServiceTest {


    private TokenService tokenService;
    private CommonService commonService;
    private CategoryRepository categoryRepository;
    private CategoryInquiryService categoryInquiryService;

    @BeforeEach
    void setup() {
        tokenService = mock(TokenService.class);
        categoryRepository = mock(CategoryRepository.class);
        categoryInquiryService = new CategoryInquiryService(tokenService, commonService, categoryRepository);
    }


    @Test
    @DisplayName("[Service] 카테고리 조회 성공")
    void categoryInquirySuccess() {

        String bearerAccessToken = "Bearer accessToken";

        Category category = Category.builder()
                .name("의류")
                .build();

        ReflectionTestUtils.setField(category, "id", 1L);

        List<Category> categoryList = new ArrayList<>(Collections.singleton(category));

        CategoryListResponseDto categoryListResponseDto = CategoryListResponseDto.builder()
                .categoryList(categoryList)
                .build();

        //given
        given(tokenService.findUserRole(bearerAccessToken)).willReturn(Role.ADMIN.getKey());
        given(categoryRepository.findAll()).willReturn(categoryList);
        //when
        CategoryListResponseDto categoryListResponseDto1 = categoryInquiryService.categoryList(bearerAccessToken);


        //then
        assertThat(categoryListResponseDto.getCategoryList().get(0).getName()).isEqualTo(categoryListResponseDto1.getCategoryList().get(0).getName());
    }
}