package com.example.auth_spring.web.dto.product;

import com.example.auth_spring.web.domain.product.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductResponseDto {

    private String productName;
    private Long productPrice;

    public ProductResponseDto(Product product) {
        this.productName = product.getName();
        this.productPrice = product.getPrice();
    }
}
