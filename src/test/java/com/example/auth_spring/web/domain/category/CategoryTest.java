package com.example.auth_spring.web.domain.category;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryTest {

    @Test
    @DisplayName("[Domain] 카테고리 생성 도메인 테스트")
    void createCategory() {
        String categoryName = "의류";

        //given
        Category category = Category.builder()
                .name(categoryName)
                .build();


        //when
        //then
        assertThat(category.getName()).isEqualTo(categoryName);
    }

}