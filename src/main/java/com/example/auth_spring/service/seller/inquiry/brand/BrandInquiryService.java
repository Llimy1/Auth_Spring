package com.example.auth_spring.service.seller.inquiry.brand;

import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.service.user.token.TokenService;
import com.example.auth_spring.type.ErrorCode;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.domain.brand.BrandRepository;
import com.example.auth_spring.web.dto.brand.BrandListResponseDto;
import com.example.auth_spring.web.dto.brand.BrandResponseDto;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BrandInquiryService {

    private final TokenService tokenService;
    private final CommonService commonService;
    private final BrandRepository brandRepository;


    // 브랜드 조회
    public BrandListResponseDto brandInquiry(String bearerAccessToken) {
        tokenService.accessTokenExpiration(bearerAccessToken);

        String email = tokenService.accessTokenEmail(bearerAccessToken);

        List<BrandResponseDto> brandResponseDto = brandRepository.findBrandListByUserEmail(email);

        if (brandResponseDto.isEmpty()) {
            throw new NotFoundException(ErrorCode.BRAND_NOT_FOUND);
        }

        return BrandListResponseDto.builder()
                .brandList(brandResponseDto)
                .build();
    }

    // API 반환
    public CommonResponse<Object> brandInquiryResponse(String bearerAccessToken) {
        return commonService.successResponse(SuccessCode.BRAND_ALL_INQUIRY_SUCCESS.getDescription(), HttpStatus.OK, brandInquiry(bearerAccessToken));

    }
}
