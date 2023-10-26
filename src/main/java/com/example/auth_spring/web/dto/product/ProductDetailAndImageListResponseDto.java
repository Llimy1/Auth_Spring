package com.example.auth_spring.web.dto.product;

import com.example.auth_spring.web.dto.image.ImageResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ProductDetailAndImageListResponseDto {

    private ProductDetailResponseDto productDetail;
    private List<ImageResponseDto> imageUrlList;

    @Builder
    public ProductDetailAndImageListResponseDto(ProductDetailResponseDto productDetail, List<ImageResponseDto> imageUrlList) {
        this.productDetail = productDetail;
        this.imageUrlList = imageUrlList;
    }
}
