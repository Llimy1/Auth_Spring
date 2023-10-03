package com.example.auth_spring.service.admin.category.update;

import com.example.auth_spring.service.user.token.TokenService;
import com.example.auth_spring.service.admin.category.update.CategoryUpdateService;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.type.Role;
import com.example.auth_spring.web.domain.category.Category;
import com.example.auth_spring.web.domain.category.CategoryRepository;
import com.example.auth_spring.web.dto.category.CategoryUpdateResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class CategoryUpdateServiceTest {

    private TokenService tokenService;
    private CommonService commonService;
    private CategoryRepository categoryRepository;
    private CategoryUpdateService categoryUpdateService;

    @BeforeEach
    void setup() {
        tokenService = mock(TokenService.class);
        categoryRepository = mock(CategoryRepository.class);
        categoryUpdateService = new CategoryUpdateService(tokenService, commonService, categoryRepository);
    }

    @Test
    @DisplayName("[Service] 카테고리 수정 성공")
    void categoryUpdateSuccess() {

        String bearerAccessToken = "Bearer accessToken";
        String beforeCategoryName = "의류";
        String afterCategoryName = "잡화";

        Category category = Category.builder()
                .name(beforeCategoryName)
                .build();

        CategoryUpdateResponseDto categoryUpdateResponseDto = CategoryUpdateResponseDto.builder()
                .beforeCategoryName(beforeCategoryName)
                        .afterCategoryName(afterCategoryName)
                                .build();

        //given
        given(tokenService.findUserRole(bearerAccessToken)).willReturn(Role.ADMIN.getKey());
        given(categoryRepository.findByName(any())).willReturn(Optional.of(category));

        //when
        CategoryUpdateResponseDto categoryUpdateResponseDto1 = categoryUpdateService.categoryUpdate(bearerAccessToken, beforeCategoryName, afterCategoryName);


        //then
        assertThat(categoryUpdateResponseDto.getAfterCategoryName()).isEqualTo(categoryUpdateResponseDto1.getAfterCategoryName());

    }
}