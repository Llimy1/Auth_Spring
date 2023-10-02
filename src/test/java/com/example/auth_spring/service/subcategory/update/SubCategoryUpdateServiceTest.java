package com.example.auth_spring.service.subcategory.update;

import com.example.auth_spring.security.jwt.service.TokenService;
import com.example.auth_spring.service.category.update.CategoryUpdateService;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.type.Role;
import com.example.auth_spring.web.domain.category.Category;
import com.example.auth_spring.web.domain.category.CategoryRepository;
import com.example.auth_spring.web.domain.subcategory.SubCategory;
import com.example.auth_spring.web.domain.subcategory.SubCategoryRepository;
import com.example.auth_spring.web.dto.subcategory.SubCategoryResponseDto;
import com.example.auth_spring.web.dto.subcategory.SubCategoryUpdateResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class SubCategoryUpdateServiceTest {

    private TokenService tokenService;
    private CommonService commonService;
    private SubCategoryRepository subCategoryRepository;
    private SubCategoryUpdateService subCategoryUpdateService;

    @BeforeEach
    void setup() {
        tokenService = mock(TokenService.class);
        subCategoryRepository = mock(SubCategoryRepository.class);
        subCategoryUpdateService = new SubCategoryUpdateService(tokenService, commonService, subCategoryRepository);
    }

    @Test
    @DisplayName("[Service] 서브 카테고리 수정 성공")
    void subCategoryUpdateSuccess() {

        String bearerAccessToken = "Bearer accessToken";
        String beforeSubCategoryName = "맨투맨";
        String afterSubCategoryName = "후드티";

        Category category = Category.builder()
                .name("의류")
                .build();

        //given
        SubCategory subCategory = SubCategory.builder()
                .category(category)
                .build();

        SubCategoryUpdateResponseDto subCategoryUpdateResponseDto = SubCategoryUpdateResponseDto.builder()
                .beforeSubCategoryName(beforeSubCategoryName)
                .afterSubCategoryName(afterSubCategoryName)
                .build();

        given(tokenService.findUserRole(any())).willReturn(Role.ADMIN.getKey());
        given(subCategoryRepository.findByName(any())).willReturn(Optional.of(subCategory));

        //when
        SubCategoryUpdateResponseDto subCategoryUpdateResponseDto1 = subCategoryUpdateService.subCategoryUpdate(bearerAccessToken, beforeSubCategoryName, afterSubCategoryName);


        //then
        assertThat(subCategoryUpdateResponseDto1.getAfterSubCategoryName())
                .isEqualTo(subCategoryUpdateResponseDto.getAfterSubCategoryName());
    }
}