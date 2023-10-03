package com.example.auth_spring.service.user.inquiry;

import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.type.ErrorCode;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.domain.product.ProductRepository;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.Pagination;
import com.example.auth_spring.web.dto.product.ProductListResponseDto;
import com.example.auth_spring.web.dto.product.ProductResponseDto;
import com.example.auth_spring.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class AllProductInquiryService {

    private final ProductRepository productRepository;
    private final CommonService commonService;

    // 전체 상품 조회
    // 수정 시간이 가장 최근순으로 정렬
    public ProductListResponseDto allProductInquiry(int page, int size, String sortBy) {

        PageRequest pageable = PageRequest.of(page - 1, size, Sort.by(sortBy).descending());

        Page<ProductResponseDto> data = productRepository.findAll(pageable).map(ProductResponseDto::new);

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

    // API 반환
    public CommonResponse<Object> allProductInquiryResponse(int page, int size, String sortBy) {
        return commonService.successResponse(SuccessCode.ALL_PRODUCT_INQUIRY_SUCCESS.getDescription(), HttpStatus.OK, allProductInquiry(page, size, sortBy));
    }

}
