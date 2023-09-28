package com.example.auth_spring.web.dto.product;

import com.example.auth_spring.web.dto.common.Pagination;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ProductListResponseDto {

    @ApiModelProperty(example = "상품 리스트 반환")
    private List<ProductResponseDto> productList;
    private Pagination pagination;

    @Builder
    public ProductListResponseDto(List<ProductResponseDto> productList, Pagination pagination) {
        this.productList = productList;
        this.pagination = pagination;
    }
}
