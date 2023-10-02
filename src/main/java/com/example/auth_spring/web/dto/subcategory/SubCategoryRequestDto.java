package com.example.auth_spring.web.dto.subcategory;

import com.example.auth_spring.web.domain.category.Category;
import com.example.auth_spring.web.domain.subcategory.SubCategory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SubCategoryRequestDto {

    @ApiModelProperty(name = "subCategoryName", value = "서브 카테고리명", example = "맨투맨")
    private String subCategoryName;


    @Builder
    public SubCategoryRequestDto(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public SubCategory toSubCategoryEntity(Category category) {
        return SubCategory.builder()
                .category(category)
                .name(subCategoryName)
                .build();
    }
}
