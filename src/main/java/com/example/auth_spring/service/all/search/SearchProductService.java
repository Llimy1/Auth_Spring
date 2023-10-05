package com.example.auth_spring.service.all.search;

import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.domain.product.ProductRepository;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.search.SearchProductListResponseDto;
import com.example.auth_spring.web.dto.search.SearchProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SearchProductService {

    private final ProductRepository productRepository;
    private final CommonService commonService;


    // 상품 이름 검색
    // 가장 최근순으로 정렬
    public SearchProductListResponseDto searchProductList(String keyword, int page, int size, String sortBy) {

        PageRequest pageable = PageRequest.of(page - 1, size, Sort.by(sortBy).descending());

        Page<SearchProductResponseDto> data = productRepository.findAllByNameContaining(keyword, pageable).map(SearchProductResponseDto::new);

        return SearchProductListResponseDto.getSearchProductListResponseDto(data);
    }

    public CommonResponse<Object> searchProductListResponse(String keyword, int page, int size, String sortBy) {
        return commonService.successResponse(SuccessCode.SEARCH_PRODUCT_SUCCESS.getDescription(), HttpStatus.OK, searchProductList(keyword, page, size, sortBy));
    }


}
