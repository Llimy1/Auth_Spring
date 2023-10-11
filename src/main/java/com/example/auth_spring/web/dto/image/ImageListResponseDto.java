package com.example.auth_spring.web.dto.image;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ImageListResponseDto {

    private Long productId;
    private List<ImageResponseDto> imageUrlList;

    @Builder
    public ImageListResponseDto(Long productId, List<ImageResponseDto> imageUrlList) {
        this.productId = productId;
        this.imageUrlList = imageUrlList;
    }
}
