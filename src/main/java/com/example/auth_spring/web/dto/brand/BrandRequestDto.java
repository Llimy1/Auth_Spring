package com.example.auth_spring.web.dto.brand;


import com.example.auth_spring.web.domain.brand.Brand;
import com.example.auth_spring.web.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BrandRequestDto {

    private String brandName;

    @Builder
    public BrandRequestDto(String brandName) {
        this.brandName = brandName;
    }

    public Brand toBrandEntity(User user) {
        return Brand.builder()
                .name(brandName)
                .user(user)
                .build();
    }
}
