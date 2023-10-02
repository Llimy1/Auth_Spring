package com.example.auth_spring.web.dto.category;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryUpdateResponseDto {

    String beforeCategoryName;
    String afterCategoryName;

    @Builder
    public CategoryUpdateResponseDto(String beforeCategoryName, String afterCategoryName) {
        this.beforeCategoryName = beforeCategoryName;
        this.afterCategoryName = afterCategoryName;
    }
}
