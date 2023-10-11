package com.example.auth_spring.web.dto.image;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ImageListResponseDto {

    private List<ImageResponseDto> imageList;

    @Builder
    public ImageListResponseDto(List<ImageResponseDto> imageList) {
        this.imageList = imageList;
    }
}
