package com.example.auth_spring.web.dto.order;

import com.example.auth_spring.type.ErrorCode;
import com.example.auth_spring.web.dto.common.Pagination;
import com.example.auth_spring.web.exception.NotFoundException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@NoArgsConstructor
public class OrderProductAllListResponseDto {

    private List<OrderProductAllResponseDto> orderProductAllList;
    private Pagination pagination;

    @Builder
    public OrderProductAllListResponseDto(List<OrderProductAllResponseDto> orderProductAllList, Pagination pagination) {
        this.orderProductAllList = orderProductAllList;
        this.pagination = pagination;
    }

    public static OrderProductAllListResponseDto getOrderProductListResponseDto(Page<OrderProductAllResponseDto> data) {

        if (data.isEmpty()) {
            throw new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND);
        }

        Pagination pagination = Pagination.builder()
                .totalPages(data.getTotalPages())
                .totalElements(data.getTotalElements())
                .pageNo(data.getNumber())
                .isLastPage(data.isLast())
                .build();

        return OrderProductAllListResponseDto.builder()
                .orderProductAllList(data.getContent())
                .pagination(pagination)
                .build();
    }
}
