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
    private Boolean isDiscount;
    private Integer discountRate;
    private Integer productCount;
    private String productSize;
    private String productColor;

    @Builder
    public CartResponseDto(String productName, Long productPrice, String brandName, Boolean isDiscount, Integer discountRate, Integer productCount, String productSize, String productColor) {
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
        this.productCount = productCount;
        this.productSize = productSize;
        this.productColor = productColor;
    }
}
