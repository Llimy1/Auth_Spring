package com.example.auth_spring.web.dto.product;

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
public class ProductListResponseDto {

    @ApiModelProperty(example = "판매자 상품 리스트 반환")
    private List<ProductResponseDto> productList;
    private Pagination pagination;

    @Builder
    public ProductListResponseDto(List<ProductResponseDto> productList, Pagination pagination) {
        this.productList = productList;
        this.pagination = pagination;
    }

    public static ProductListResponseDto getProductListResponseDto(Page<ProductResponseDto> data) {

        if (data.isEmpty()) {
            throw new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND);
        }

        Pagination pagination = Pagination.builder()
                .totalPages(data.getTotalPages())
                .totalElements(data.getTotalElements())
                .pageNo(data.getNumber())
                .isLastPage(data.isLast())
                .build();

        return ProductListResponseDto.builder()
                .productList(data.getContent())
                .pagination(pagination)
                .build();
    }
}
