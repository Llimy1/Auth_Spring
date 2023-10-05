package com.example.auth_spring.web.dto.order;

import com.example.auth_spring.web.domain.address.Address;
import com.example.auth_spring.web.domain.order.Order;
import com.example.auth_spring.web.domain.product.Product;
import com.example.auth_spring.web.domain.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Random;

@Getter
@NoArgsConstructor
public class OrderProductRequestDto {

    @ApiModelProperty(name = "orderName", value = "orderName", example = "aB8Xp3Kw")
    private String orderName;
    @ApiModelProperty(name = "count", value = "count", example = "1")
    private Integer count;
    @ApiModelProperty(name = "addressId", value = "addressId", example = "1")
    private Long addressId;
    @ApiModelProperty(name = "productName", value = "productName", example = "나이키 후드티")
    private String productName;

    @Builder
    public OrderProductRequestDto(Integer count, Long addressId, String productName) {
        this.orderName = createName();
        this.count = count;
        this.addressId = addressId;
        this.productName = productName;
    }

    public Order toOrderEntity(User user, Product product, Address address) {
        return Order.builder()
                .user(user)
                .product(product)
                .address(address)
                .orderName(orderName)
                .count(count)
                .totalPrice(product.getPrice() + product.getDeliveryPrice())
                .build();
    }


    // 8자리 이름 무작위 생성
    private String createName() {
        StringBuilder name = new StringBuilder();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) {
            int index = rnd.nextInt(3);

            switch (index) {
                case 0:
                    // 소문자 무작위
                    name.append((char) (rnd.nextInt(26)) + 97);
                    break;
                case 1:
                    // 대문자 무작위
                    name.append((char) (rnd.nextInt(26)) + 65);
                    break;
                case 2:
                    // 숫자 무작위
                    name.append((rnd.nextInt(10)));
                    break;
            }
        }
        return name.toString();
    }


}
