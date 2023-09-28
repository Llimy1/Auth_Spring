package com.example.auth_spring.web.dto.product;

import com.example.auth_spring.web.domain.product.Product;
import com.example.auth_spring.web.domain.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductRequestDto {

    @ApiModelProperty(name = "productName", value = "productName", example = "후드티")
    private String productName;
    @ApiModelProperty(name = "productPrice", value = "productPrice", example = "10000L")
    private Long productPrice;

    @Builder
    public ProductRequestDto(String productName, Long productPrice) {
        this.productName = productName;
        this.productPrice = productPrice;
    }

    public Product toProductEntity(User user) {
        return Product.builder()
                .user(user)
                .name(productName)
                .price(productPrice)
                .build();
    }
}
