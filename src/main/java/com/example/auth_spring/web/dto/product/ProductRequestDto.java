package com.example.auth_spring.web.dto.product;
import com.example.auth_spring.web.domain.product.Product;
import com.example.auth_spring.web.domain.subcategory.SubCategory;
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
    @ApiModelProperty(name = "deliveryPrice", value = "deliveryPrice", example = "3000")
    private Integer deliveryPrice;


    @Builder
    public ProductRequestDto(String productName, Long productPrice, Integer deliveryPrice) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.deliveryPrice = deliveryPrice;
    }

    public Product toProductEntity(User user, SubCategory subCategory) {
        return Product.builder()
                .user(user)
                .subCategory(subCategory)
                .name(productName)
                .price(productPrice)
                .deliveryPrice(deliveryPrice)
                .build();
    }
}
