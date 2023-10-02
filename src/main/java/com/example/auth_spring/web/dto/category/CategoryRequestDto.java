package com.example.auth_spring.web.dto.category;

import com.example.auth_spring.web.domain.category.Category;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryRequestDto {

    @ApiModelProperty(name = "categoryName", value = "카테고리명", example = "의류")
    private String categoryName;

    @Builder
    public CategoryRequestDto(String categoryName) {
        this.categoryName = categoryName;
    }

    public Category toCategoryEntity() {
        return Category.builder()
                .name(categoryName)
                .build();
    }
}
