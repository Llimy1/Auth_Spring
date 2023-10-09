package com.example.auth_spring.web.dto.order;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderProductDetailResponseDto {

    private String userName;
    private String productName;
    private Long productPrice;
    private String productBrand;
    private String optionName;
    private Integer deliveryPrice;
    private Long totalOrderPrice;
    private String zipCode;
    private String streetAddress;
    private String detailAddress;
    private Integer orderProductCount;


    @Builder
    public OrderProductDetailResponseDto(String userName, String productName, Long productPrice, String productBrand, String optionName, Integer deliveryPrice, Long totalOrderPrice, String zipCode, String streetAddress, String detailAddress, Integer orderProductCount) {
        this.userName = userName;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productBrand = productBrand;
        this.optionName = optionName;
        this.deliveryPrice = deliveryPrice;
        this.totalOrderPrice = totalOrderPrice;
        this.zipCode = zipCode;
        this.streetAddress = streetAddress;
        this.detailAddress = detailAddress;
        this.orderProductCount = orderProductCount;
    }
}