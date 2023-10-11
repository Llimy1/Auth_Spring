package com.example.auth_spring.service.all.inquiry;

import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.type.ErrorCode;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.domain.image.ImageRepository;
import com.example.auth_spring.web.domain.product.ProductRepository;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.Pagination;
import com.example.auth_spring.web.dto.image.ImageListResponseDto;
import com.example.auth_spring.web.dto.image.ImageResponseDto;
import com.example.auth_spring.web.dto.product.ProductListResponseDto;
import com.example.auth_spring.web.dto.product.ProductResponseDto;
import com.example.auth_spring.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class AllProductInquiryService {

    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;
    private final CommonService commonService;

    // 전체 상품 조회
    // 수정 시간이 가장 최근순으로 정렬
    public ProductListResponseDto allProductInquiry(int page, int size, String sortBy) {

        PageRequest pageable = PageRequest.of(page - 1, size, Sort.by(sortBy).descending());

        Page<ProductResponseDto> data = productRepository.findProductAllList(pageable);

        List<ProductResponseDto> productList = data.getContent();

        List<ImageListResponseDto> productImages = new ArrayList<>();

        for (ProductResponseDto product : productList) {
            List<ImageResponseDto> productImagesList = imageRepository.findAllImageList(product.getProductId());
            ImageListResponseDto productImage = ImageListResponseDto.builder()
                    .productId(product.getProductId())
                    .imageUrlList(productImagesList)
                    .build();
            productImages.add(productImage);
        }

        return ProductListResponseDto.getProductListResponseDto(data, productImages);
    }

    // API 반환
    public CommonResponse<Object> allProductInquiryResponse(int page, int size, String sortBy) {
        return commonService.successResponse(SuccessCode.ALL_PRODUCT_INQUIRY_SUCCESS.getDescription(), HttpStatus.OK, allProductInquiry(page, size, sortBy));
    }
}
