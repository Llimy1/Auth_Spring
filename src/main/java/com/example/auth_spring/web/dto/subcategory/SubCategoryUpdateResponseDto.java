package com.example.auth_spring.web.dto.subcategory;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SubCategoryUpdateResponseDto {

    @ApiModelProperty(name = "beforeSubCategoryName", value = "이전 서브 카테고리명", example = "맨투맨")
    String beforeSubCategoryName;
    @ApiModelProperty(name = "afterSubCategoryName", value = "바꿀 서브 카테고리명", example = "후드티")
    String afterSubCategoryName;


    @Builder
    public SubCategoryUpdateResponseDto(String beforeSubCategoryName, String afterSubCategoryName) {
        this.beforeSubCategoryName = beforeSubCategoryName;
        this.afterSubCategoryName = afterSubCategoryName;
    }
}
