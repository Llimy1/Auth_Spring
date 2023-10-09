package com.example.auth_spring.web.dto.product;
import com.example.auth_spring.web.domain.brand.Brand;
import com.example.auth_spring.web.domain.product.Product;
import com.example.auth_spring.web.domain.subcategory.SubCategory;
import com.example.auth_spring.web.domain.user.User;
import com.example.auth_spring.web.domain.view.View;
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
    @ApiModelProperty(name = "brandName", value = "brandName", example = "3000")
    private String brandName;
    @ApiModelProperty(name = "discountRate", value = "discountRate", example = "35")
    private Integer discountRate;
    @ApiModelProperty(name = "isDisCount", value = "isDisCount", example = "true")
    private Boolean isDiscount;


    @Builder
    public ProductRequestDto(String productName, Long productPrice, Integer deliveryPrice, String brandName, Integer discountRate, Boolean isDiscount) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.deliveryPrice = deliveryPrice;
        this.brandName = brandName;
        this.discountRate = discountRate;
        this.isDiscount = isDiscount;
    }

    public Product toProductEntity(User user, SubCategory subCategory, Brand brand) {
        return Product.builder()
                .user(user)
                .subCategory(subCategory)
                .brand(brand)
                .name(productName)
                .price(productPrice)
                .deliveryPrice(deliveryPrice)
                .discountRate(discountRate)
                .isDiscount(isDiscount)
                .likeCount(0L)
                .build();
    }

    public View toViewEntity(Product product) {
        return View.builder()
                .count(0L)
                .product(product)
                .build();
    }
}
