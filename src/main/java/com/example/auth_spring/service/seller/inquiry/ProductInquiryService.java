package com.example.auth_spring.service.seller.inquiry;

import com.example.auth_spring.service.user.token.TokenService;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.type.ErrorCode;
import com.example.auth_spring.type.Role;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.domain.product.ProductRepository;
import com.example.auth_spring.web.domain.user.User;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.Pagination;
import com.example.auth_spring.web.dto.product.ProductListResponseDto;
import com.example.auth_spring.web.dto.product.ProductResponseDto;
import com.example.auth_spring.web.exception.IllegalStateException;
import com.example.auth_spring.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductInquiryService {

    private final ProductRepository productRepository;
    private final TokenService tokenService;
    private final CommonService commonService;


    // 판매자 상품 조회
    // 수정 시간이 가장 최근순으로 정렬
    public ProductListResponseDto productInquiry(String bearerAccessToken, int page, int size, String sortBy) {

        tokenService.accessTokenExpiration(bearerAccessToken);

        User user = tokenService.findUser(bearerAccessToken);

        if (!user.getRoleKey().equals(Role.SELLER.getKey())) {
            throw new IllegalStateException(ErrorCode.AUTHORITY_NOT_SELLER);
        }

        Long userId = user.getId();

        PageRequest pageable = PageRequest.of(page - 1, size, Sort.by(sortBy).descending());

        Page<ProductResponseDto> data = productRepository.findAllByUserId(userId, pageable).map(ProductResponseDto::new);

        if (data.isEmpty()) {
            throw new NotFoundException(ErrorCode.SELLER_PRODUCT_NOT_FOUND);
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
    public CommonResponse<Object> productInquiryResponse(String bearAccessToken, int page, int size, String sortBy) {
        return commonService.successResponse(SuccessCode.SELLER_PRODUCT_INQUIRY_SUCCESS.getDescription(), HttpStatus.OK, productInquiry(bearAccessToken, page, size, sortBy));
    }
}
