package com.example.auth_spring.web.dto.subcategory;

import com.example.auth_spring.web.domain.subcategory.SubCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SubCategoryResponseDto {

    private String subCategoryName;

    @Builder
    public SubCategoryResponseDto(SubCategory subCategory) {
        this.subCategoryName = subCategory.getName();
    }
}
