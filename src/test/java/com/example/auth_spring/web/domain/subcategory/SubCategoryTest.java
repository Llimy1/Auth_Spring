package com.example.auth_spring.web.domain.subcategory;

import com.example.auth_spring.web.domain.category.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SubCategoryTest {


    @Test
    @DisplayName("[Domain] 서브 카테고리 생성 도메인 테스트")
    void createSubCategory() {
        String categoryName = "의류";

        Category category = Category.builder()
                .name(categoryName)
                .build();

        ReflectionTestUtils.setField(category, "id", 1L);

        String subCategoryName = "맨투맨";

        //given
        SubCategory subCategory = SubCategory.builder()
                .category(category)
                .name(subCategoryName)
                .build();

        //when
        //then
        assertThat(subCategory.getCategory().getId()).isEqualTo(1L);
        assertThat(subCategory.getName()).isEqualTo(subCategoryName);

    }
}