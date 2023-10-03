package com.example.auth_spring.web.dto.cart;

import com.example.auth_spring.web.dto.common.Pagination;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CartListResponseDto {

    @ApiModelProperty(example = "장바구니 상품 리스트 반환")
    private List<CartResponseDto> cartList;
    private Pagination pagination;

    @Builder
    public CartListResponseDto(List<CartResponseDto> cartList, Pagination pagination) {
        this.cartList = cartList;
        this.pagination = pagination;
    }
}
