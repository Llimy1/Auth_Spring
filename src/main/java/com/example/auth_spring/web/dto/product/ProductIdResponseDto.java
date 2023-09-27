package com.example.auth_spring.web.dto.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductIdResponseDto {

    @ApiModelProperty(example = "해당 상품 번호")
    private Long productId;

    public ProductIdResponseDto(Long productId) {
        this.productId = productId;
    }
}
