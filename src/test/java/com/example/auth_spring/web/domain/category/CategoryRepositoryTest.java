package com.example.auth_spring.web.domain.category;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("[Repository] 카테고리 생성하기")
    void createCategory() {
        String categoryName1 = "의류";
        String categoryName2 = "악세사리";

        //given
        Category category1 = Category.builder()
                .name(categoryName1)
                .build();

        Category category2 = Category.builder()
                .name(categoryName2)
                .build();

        //when
        Category result1 = categoryRepository.save(category1);
        Category result2 = categoryRepository.save(category2);

        //then
        assertThat(result1.getName()).isEqualTo(categoryName1);
        assertThat(result2.getName()).isEqualTo(categoryName2);
    }
}