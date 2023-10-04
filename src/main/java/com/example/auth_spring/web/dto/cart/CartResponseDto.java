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

    @Builder
    public CartResponseDto(String productName, Long productPrice) {
        this.productName = productName;
        this.productPrice = productPrice;
    }
}
