package com.example.auth_spring.web.dto.seller;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SellerProductResponseDto {

    private String productName;
    private Long productPrice;
    private String brandName;
    private Boolean isDiscount;
    private Integer discountRate;
    private Long viewCount;
    private Long likeCount;
    private String mainProductImageUrl;
    private Integer productStock;

    @Builder
    public SellerProductResponseDto(String productName, Long productPrice, String brandName, Boolean isDiscount, Integer discountRate, Long viewCount, Long likeCount, String mainProductImageUrl, Integer productStock) {
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
        this.mainProductImageUrl = mainProductImageUrl;
        this.productStock = productStock;
    }
}
