package com.example.auth_spring.web.dto.product;

import com.example.auth_spring.web.domain.product.Product;
import com.example.auth_spring.web.domain.subcategory.SubCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductResponseDto {

    private String productName;
    private Long productPrice;
    private String brandName;

    @Builder
    public ProductResponseDto(String productName, Long productPrice, String brandName) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.brandName = brandName;
    }
}
