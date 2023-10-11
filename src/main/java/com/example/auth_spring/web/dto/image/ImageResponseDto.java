package com.example.auth_spring.web.dto.image;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ImageResponseDto {

    private List<String> imageUrl;

    @Builder
    public ImageResponseDto(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }
}
