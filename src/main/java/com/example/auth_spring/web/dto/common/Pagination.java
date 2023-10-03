package com.example.auth_spring.web.dto.common;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Pagination {

    private int totalPages;
    private Long totalElements;
    private int pageNo;
    private boolean isLastPage;

    @Builder
    public Pagination(int totalPages, Long totalElements, int pageNo, boolean isLastPage) {
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.pageNo = pageNo;
        this.isLastPage = isLastPage;
    }
}
