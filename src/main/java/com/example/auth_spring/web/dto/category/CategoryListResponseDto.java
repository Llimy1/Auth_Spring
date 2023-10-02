package com.example.auth_spring.web.dto.category;

import com.example.auth_spring.web.domain.category.Category;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CategoryListResponseDto {

    @ApiModelProperty(example = "카테고리 리스트")
    private List<Category> categoryList;

    @Builder
    public CategoryListResponseDto(List<Category> categoryList) {
        this.categoryList = categoryList;
    }
}
