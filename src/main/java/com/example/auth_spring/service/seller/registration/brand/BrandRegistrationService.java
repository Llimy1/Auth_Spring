package com.example.auth_spring.service.seller.registration.brand;

import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.service.user.token.TokenService;
import com.example.auth_spring.type.ErrorCode;
import com.example.auth_spring.type.Role;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.domain.brand.Brand;
import com.example.auth_spring.web.domain.brand.BrandRepository;
import com.example.auth_spring.web.domain.user.User;
import com.example.auth_spring.web.dto.brand.BrandRequestDto;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.exception.IllegalStateException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BrandRegistrationService {

    private final TokenService tokenService;
    private final BrandRepository brandRepository;
    private final CommonService commonService;

    // 브랜드 저장
    @Transactional
    public void brandRegistration(String bearerAccessToken , BrandRequestDto brandRequestDto) {
        tokenService.accessTokenExpiration(bearerAccessToken);

        User user = tokenService.findUser(bearerAccessToken);

        if (!user.getRole().equals(Role.SELLER)) {
            throw new IllegalStateException(ErrorCode.AUTHORITY_NOT_SELLER);
        }

        Brand brand = brandRequestDto.toBrandEntity(user);

        brandRepository.save(brand);
    }

    // API 반환
    @Transactional
    public CommonResponse<Object> brandRegistrationResponse(String bearerAccessToken, BrandRequestDto brandRequestDto) {
        brandRegistration(bearerAccessToken, brandRequestDto);
        return commonService.successResponse(SuccessCode.BRAND_REGISTRATION_SUCCESS.getDescription(), HttpStatus.CREATED, null);
    }


}
