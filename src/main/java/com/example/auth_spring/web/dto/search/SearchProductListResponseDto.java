package com.example.auth_spring.web.dto.search;

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
public class SearchProductListResponseDto {

    @ApiModelProperty(example = "검색 상품 리스트 반환")
    private List<SearchProductResponseDto> searchProductList;
    private Pagination pagination;

    @Builder
    public SearchProductListResponseDto(List<SearchProductResponseDto> searchProductList, Pagination pagination) {
        this.searchProductList = searchProductList;
        this.pagination = pagination;
    }

    public static SearchProductListResponseDto getSearchProductListResponseDto(Page<SearchProductResponseDto> data) {

        if (data.isEmpty()) {
            throw new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND);
        }

        Pagination pagination = Pagination.builder()
                .totalPages(data.getTotalPages())
                .totalElements(data.getTotalElements())
                .pageNo(data.getNumber())
                .isLastPage(data.isLast())
                .build();

        return SearchProductListResponseDto.builder()
                .searchProductList(data.getContent())
                .pagination(pagination)
                .build();
    }
}
