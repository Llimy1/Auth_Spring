package com.example.auth_spring.service.all.inquiry;

import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.type.ErrorCode;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.domain.image.ImageRepository;
import com.example.auth_spring.web.domain.product.ProductRepository;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.image.ImageResponseDto;
import com.example.auth_spring.web.dto.product.ProductDetailAndImageListResponseDto;
import com.example.auth_spring.web.dto.product.ProductDetailResponseDto;
import com.example.auth_spring.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DetailProductInquiryService {

    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;
    private final CommonService commonService;

    // 상세 정보 조회
    public ProductDetailAndImageListResponseDto detailProductInquiry(String productName) {
        ProductDetailResponseDto productDetailResponseDto = productRepository.findProductDetailByProductName(productName)
                .orElseThrow(() -> new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND));

        List<ImageResponseDto> productImageList  = imageRepository.findAllImageList(productDetailResponseDto.getProductId());

        return ProductDetailAndImageListResponseDto.builder()
                .productDetail(productDetailResponseDto)
                .imageUrlList(productImageList)
                .build();
    }


    // API 반환
    public CommonResponse<Object> detailProductInquiryResponse(String productName) {
        return commonService.successResponse(SuccessCode.PRODUCT_DETAIL_INQUIRY_SUCCESS.getDescription(), HttpStatus.OK, detailProductInquiry(productName));
    }
}
