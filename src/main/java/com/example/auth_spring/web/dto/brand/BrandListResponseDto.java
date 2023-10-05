package com.example.auth_spring.web.dto.brand;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class BrandListResponseDto {

    private List<BrandResponseDto> brandList;

    @Builder
    public BrandListResponseDto(List<BrandResponseDto> brandList) {
        this.brandList = brandList;
    }
}
