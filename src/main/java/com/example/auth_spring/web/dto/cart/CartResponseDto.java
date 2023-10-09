package com.example.auth_spring.web.dto.cart;

import com.example.auth_spring.web.domain.cart.Cart;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartResponseDto {

    private String productName;
    private Long productPrice;
    private String brandName;
    private String optionName;
    private Boolean isDiscount;
    private Integer discountRate;

    @Builder
    public CartResponseDto(String productName, Long productPrice, String brandName, String optionName, Boolean isDiscount, Integer discountRate) {
        this.productName = productName;
        this.brandName = brandName;
        this.optionName = optionName;
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
