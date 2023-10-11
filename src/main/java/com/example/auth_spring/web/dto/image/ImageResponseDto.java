package com.example.auth_spring.web.dto.image;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ImageResponseDto {

    private Integer imageSequence;
    private String imageUrl;

    @Builder
    public ImageResponseDto(Integer imageSequence, String imageUrl) {
        this.imageSequence = imageSequence;
        this.imageUrl = imageUrl;
    }
}
