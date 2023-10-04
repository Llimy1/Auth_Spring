package com.example.auth_spring.service.admin.subcategory.Inquiry;

import com.example.auth_spring.service.user.token.TokenService;
import com.example.auth_spring.service.admin.subcategory.Inquiry.SubCategoryInquiryService;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.type.Role;
import com.example.auth_spring.web.domain.category.Category;
import com.example.auth_spring.web.domain.category.CategoryRepository;
import com.example.auth_spring.web.domain.subcategory.SubCategory;
import com.example.auth_spring.web.domain.subcategory.SubCategoryRepository;
import com.example.auth_spring.web.dto.subcategory.SubCategoryListResponseDto;
import com.example.auth_spring.web.dto.subcategory.SubCategoryResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class SubCategoryInquiryServiceTest {

    private TokenService tokenService;
    private CommonService commonService;
    private SubCategoryRepository subCategoryRepository;
    private CategoryRepository categoryRepository;
    private SubCategoryInquiryService subCategoryInquiryService;

    @BeforeEach
    void setup() {
        tokenService = mock(TokenService.class);
        subCategoryRepository = mock(SubCategoryRepository.class);
        categoryRepository = mock(CategoryRepository.class);
        subCategoryInquiryService = new SubCategoryInquiryService(tokenService, commonService, categoryRepository, subCategoryRepository);
    }

    @Test
    @DisplayName("[Service] 서브 카테고리 조회 성공")
    void subCategoryInquirySuccess() {

        String bearerAccessToken = "Bearer accessToken";

        Category category = Category.builder()
                .name("의류")
                .build();
        ReflectionTestUtils.setField(category, "id", 1L);

        SubCategory subCategory = SubCategory.builder()
                .category(category)
                .name("맨투맨")
                .build();

//        List<SubCategory> subCategoryList = Collections.singletonList(subCategory);
        List<SubCategoryResponseDto> subCategoryList = List.of(SubCategoryResponseDto.builder()
                .subCategoryName(subCategory.getName())
                        .build());

        SubCategoryListResponseDto subCategoryListResponseDto1 = SubCategoryListResponseDto.builder()
                .categoryName(category.getName())
                        .subCategoryNameList(subCategoryList)
                                .build();

        //given
        given(tokenService.findUserRole(bearerAccessToken)).willReturn(Role.ADMIN.getKey());
//        given(categoryRepository.findByName(category.getName())).willReturn(Optional.of(category));
//        given(subCategoryRepository.findAllByCategoryId(category.getId())).willReturn(subCategoryList);
        given(subCategoryRepository.findSubCategoryListByCategoryName(any())).willReturn(subCategoryList);
        //when
        SubCategoryListResponseDto subCategoryListResponseDto = subCategoryInquiryService.subCategoryList(bearerAccessToken, category.getName());

        //then
//        assertThat(subCategoryListResponseDto.getCategoryName()).isEqualTo(subCategoryList.get(0).get().getName());
//        assertThat(subCategoryListResponseDto.getSubCategoryNameList().get(0).getSubCategoryName())
//                .isEqualTo(subCategoryList.get(0).getName());
        assertThat(subCategoryListResponseDto.getCategoryName()).isEqualTo(subCategoryListResponseDto1.getCategoryName());
        assertThat(subCategoryListResponseDto.getSubCategoryNameList().get(0).getSubCategoryName())
                .isEqualTo(subCategoryListResponseDto1.getSubCategoryNameList().get(0).getSubCategoryName());
    }

}