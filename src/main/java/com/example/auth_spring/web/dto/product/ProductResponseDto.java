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
    private Boolean isDiscount;
    private Integer discountRate;

    @Builder
    public ProductResponseDto(String productName, Long productPrice, String brandName, Boolean isDiscount, Integer discountRate) {
        this.productName = productName;
        this.brandName = brandName;
        this.isDiscount = isDiscount;
        if (isDiscount) {
            double discount = discountRate / 100.0;
            this.productPrice = Math.round((1 - discount) * productPrice);
            this.discountRate = discountRate;

        } else {
            this.productPrice = productPrice;
        }
    }
}
