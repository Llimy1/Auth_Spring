package com.example.auth_spring.web.domain.subcategory;

import com.example.auth_spring.web.domain.category.Category;
import com.example.auth_spring.web.domain.category.CategoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SubCategoryRepositoryTest {

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    @AfterEach
    void after() {
        subCategoryRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    @DisplayName("[Repository] 서브 카테고리 생성하기")
    void createSubCategory() {
        String categoryName = "의류";

        //given
        Category category = Category.builder()
                .name(categoryName)
                .build();

        categoryRepository.save(category);


        String subCategoryName1 = "맨투맨";
        String subCategoryName2 = "후드티";


        //given
        SubCategory subCategory1 = SubCategory.builder()
                .category(category)
                .name(subCategoryName1)
                .build();

        SubCategory subCategory2 = SubCategory.builder()
                .category(category)
                .name(subCategoryName2)
                .build();

        //when
        SubCategory result1 = subCategoryRepository.save(subCategory1);
        SubCategory result2 = subCategoryRepository.save(subCategory2);

        //then
        assertThat(result1.getName()).isEqualTo(subCategoryName1);
        assertThat(result2.getName()).isEqualTo(subCategoryName2);
    }
}