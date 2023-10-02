package com.example.auth_spring.web.dto.subcategory;

import com.example.auth_spring.web.domain.subcategory.SubCategory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class SubCategoryListResponseDto {

    @ApiModelProperty(example = "카테고리명")
    private String categoryName;

    @ApiModelProperty(example = "서브 카테고리 리스트")
    private List<SubCategoryResponseDto> subCategoryNameList;

    @Builder
    public SubCategoryListResponseDto(String categoryName, List<SubCategoryResponseDto> subCategoryNameList) {
        this.categoryName = categoryName;
        this.subCategoryNameList = subCategoryNameList;
    }
}
