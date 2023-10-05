package com.example.auth_spring.web.dto.brand;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BrandResponseDto {

    private String brandName;

    @Builder
    public BrandResponseDto(String brandName) {
        this.brandName = brandName;
    }
}
