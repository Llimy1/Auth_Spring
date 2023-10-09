package com.example.auth_spring.service.seller.registration.product;

import com.example.auth_spring.service.user.token.TokenService;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.type.ErrorCode;
import com.example.auth_spring.type.Role;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.domain.brand.Brand;
import com.example.auth_spring.web.domain.brand.BrandRepository;
import com.example.auth_spring.web.domain.product.Product;
import com.example.auth_spring.web.domain.product.ProductRepository;
import com.example.auth_spring.web.domain.subcategory.SubCategory;
import com.example.auth_spring.web.domain.subcategory.SubCategoryRepository;
import com.example.auth_spring.web.domain.user.User;
import com.example.auth_spring.web.domain.view.View;
import com.example.auth_spring.web.domain.view.ViewRepository;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.product.ProductIdResponseDto;
import com.example.auth_spring.web.dto.product.ProductRequestDto;
import com.example.auth_spring.web.exception.IllegalStateException;
import com.example.auth_spring.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProductRegistrationService {

    private final TokenService tokenService;
    private final CommonService commonService;
    private final ProductRepository productRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final BrandRepository brandRepository;
    private final ViewRepository viewRepository;


    // 상품 등록
    @Transactional
    public Long registration(String bearerAccessToken, String subCategoryName, ProductRequestDto productRequestDto) {
        String brandName = productRequestDto.getBrandName();

        tokenService.accessTokenExpiration(bearerAccessToken);

        User user = tokenService.findUser(bearerAccessToken);

        if (!user.getRoleKey().equals(Role.SELLER.getKey())) {
            throw new IllegalStateException(ErrorCode.AUTHORITY_NOT_SELLER);
        }

        SubCategory subCategory = subCategoryRepository.findByName(subCategoryName)
                .orElseThrow(() -> new NotFoundException(ErrorCode.SUB_CATEGORY_NOT_FOUND));

        Brand brand = brandRepository.findByName(brandName)
                .orElseThrow(() -> new NotFoundException(ErrorCode.BRAND_NOT_FOUND));

        Product product = productRequestDto.toProductEntity(user, subCategory, brand);

        Long productId  = productRepository.save(product).getId();

        View view = productRequestDto.toViewEntity(product);

        viewRepository.save(view);

        return productId;
    }

    // API 반환
    @Transactional
    public CommonResponse<Object> registrationResponse(String bearerAccessToken, String subCategoryName, ProductRequestDto productRequestDto) {
        Long productId = registration(bearerAccessToken, subCategoryName, productRequestDto);

        return commonService.successResponse(SuccessCode.PRODUCT_REGISTRATION_SUCCESS.getDescription(), HttpStatus.CREATED, new ProductIdResponseDto(productId));
    }
}
