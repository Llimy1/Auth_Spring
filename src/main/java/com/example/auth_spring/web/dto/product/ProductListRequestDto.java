package com.example.auth_spring.web.dto.product;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@NoArgsConstructor
public class ProductListRequestDto {

    private ProductRequestDto productRequest;
    private List<MultipartFile> multipartFiles;

    @Builder
    public ProductListRequestDto(ProductRequestDto productRequest, List<MultipartFile> multipartFiles) {
        this.productRequest = productRequest;
        this.multipartFiles = multipartFiles;
    }
}
