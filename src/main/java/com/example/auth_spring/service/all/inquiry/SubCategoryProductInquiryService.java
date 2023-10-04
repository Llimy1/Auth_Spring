package com.example.auth_spring.service.all.inquiry;

import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.domain.product.ProductRepository;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.product.ProductListResponseDto;
import com.example.auth_spring.web.dto.product.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class SubCategoryProductInquiryService {

    private final ProductRepository productRepository;
    private final CommonService commonService;


    // 서브 카테고리 별 상품 조회
    public ProductListResponseDto subCategoryProductInquiry(String subCategoryName, int page, int size, String sortBy) {

        PageRequest pageable = PageRequest.of(page - 1, size, Sort.by(sortBy).descending());

        Page<ProductResponseDto> data = productRepository.findProductListBySubCategoryName(subCategoryName, pageable);

        return ProductListResponseDto.getProductListResponseDto(data);
    }

    // API 반환
    public CommonResponse<Object> subCategoryProductInquiryResponse(String subCategoryName, int page, int size, String sortBy) {
        return commonService.successResponse(SuccessCode.SUB_CATEGORY_PRODUCT_INQUIRY_SUCCESS.getDescription(), HttpStatus.OK, subCategoryProductInquiry(subCategoryName, page, size, sortBy));
    }
}
