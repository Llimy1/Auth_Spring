package com.example.auth_spring.web.dto.search;

import com.example.auth_spring.web.domain.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchProductResponseDto {

    private String productName;
    private Long productPrice;

    @Builder
    public SearchProductResponseDto(Product product) {
        this.productName = product.getName();
        this.productPrice = product.getPrice();
    }
}
