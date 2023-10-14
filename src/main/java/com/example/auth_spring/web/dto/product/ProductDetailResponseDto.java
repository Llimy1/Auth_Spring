package com.example.auth_spring.web.dto.product;

import com.example.auth_spring.web.dto.option.OptionResponseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductDetailResponseDto {

    private Long productId;
    private String productName;
    private Long productPrice;
    private String brandName;
    private Boolean isDiscount;
    private Integer discountRate;
    private Long viewCount;
    private Long likeCount;
    private Integer productStock;


    @Builder
    public ProductDetailResponseDto(Long productId, String productName, Long productPrice, String brandName, Boolean isDiscount, Integer discountRate, Long viewCount, Long likeCount, Integer productStock) {
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
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.productStock = productStock;
    }

}
