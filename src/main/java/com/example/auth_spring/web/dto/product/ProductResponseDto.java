package com.example.auth_spring.web.dto.product;

import com.example.auth_spring.web.domain.image.Image;
import com.example.auth_spring.web.domain.product.Product;
import com.example.auth_spring.web.domain.subcategory.SubCategory;
import com.example.auth_spring.web.dto.image.ImageListResponseDto;
import com.example.auth_spring.web.dto.image.ImageResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ProductResponseDto {

    private Long productId;
    private String productName;
    private Long productPrice;
    private String brandName;
    private Boolean isDiscount;
    private Integer discountRate;


    @Builder
    public ProductResponseDto(Long productId, String productName, Long productPrice, String brandName, Boolean isDiscount, Integer discountRate) {
        this.productId = productId;
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
