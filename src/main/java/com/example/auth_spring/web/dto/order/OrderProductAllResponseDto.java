package com.example.auth_spring.web.dto.order;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderProductAllResponseDto {

    private String orderName;
    private String productName;
    private Long totalOrderPrice;
    private String productBrand;
    private Integer orderProductCount;
    private String optionName;

    @Builder
    public OrderProductAllResponseDto(String orderName, String productName, Long totalOrderPrice, String productBrand, Integer orderProductCount, String optionName) {
        this.orderName = orderName;
        this.productName = productName;
        this.totalOrderPrice = totalOrderPrice;
        this.productBrand = productBrand;
        this.orderProductCount = orderProductCount;
        this.optionName = optionName;
    }
}
