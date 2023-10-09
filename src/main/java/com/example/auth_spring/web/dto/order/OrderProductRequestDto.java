package com.example.auth_spring.web.dto.order;

import com.example.auth_spring.web.domain.address.Address;
import com.example.auth_spring.web.domain.order.Order;
import com.example.auth_spring.web.domain.productoption.ProductOption;
import com.example.auth_spring.web.domain.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderProductRequestDto {

    @ApiModelProperty(name = "count", value = "count", example = "1")
    private Integer count;
    @ApiModelProperty(name = "addressId", value = "addressId", example = "1")
    private Long addressId;
    @ApiModelProperty(name = "productName", value = "productName", example = "나이키 후드티")
    private String productName;
    private Long totalPrice;

    @Builder
    public OrderProductRequestDto(Integer count, Long addressId, String productName) {
        this.count = count;
        this.addressId = addressId;
        this.productName = productName;
    }

    public Order toOrderEntity(User user, ProductOption productOption, Address address, String orderName) {

        if (productOption.getProduct().getIsDiscount()) {
            double discountRate = productOption.getProduct().getDiscountRate() / 100.0;
            double doublePrice = ((1 - discountRate) * productOption.getProduct().getPrice()) + productOption.getProduct().getDeliveryPrice();
            totalPrice = Math.round(doublePrice);
        } else {
            totalPrice = productOption.getProduct().getPrice() + productOption.getProduct().getDeliveryPrice();
        }


        return Order.builder()
                .user(user)
                .productOption(productOption)
                .address(address)
                .orderName(orderName)
                .count(count)
                .totalPrice(totalPrice)
                .build();
    }
}
