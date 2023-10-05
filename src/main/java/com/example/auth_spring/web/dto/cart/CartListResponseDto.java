package com.example.auth_spring.web.dto.cart;

import com.example.auth_spring.type.ErrorCode;
import com.example.auth_spring.web.dto.common.Pagination;
import com.example.auth_spring.web.exception.NotFoundException;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

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

    public static CartListResponseDto getCartListResponseDto(Page<CartResponseDto> data) {

        if (data.isEmpty()) {
            throw new NotFoundException(ErrorCode.CART_PRODUCT_NOT_FOUND);
        }

        Pagination pagination = Pagination.builder()
                .totalPages(data.getTotalPages())
                .totalElements(data.getTotalElements())
                .pageNo(data.getNumber())
                .isLastPage(data.isLast())
                .build();

        return CartListResponseDto.builder()
                .cartList(data.getContent())
                .pagination(pagination)
                .build();
    }

}
