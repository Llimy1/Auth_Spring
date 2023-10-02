package com.example.auth_spring.service.category.registration;

import com.example.auth_spring.security.jwt.service.TokenService;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.type.ErrorCode;
import com.example.auth_spring.type.Role;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.domain.category.Category;
import com.example.auth_spring.web.domain.category.CategoryRepository;
import com.example.auth_spring.web.dto.category.CategoryRequestDto;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.exception.IllegalStateException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CategoryRegistrationService {

    private final TokenService tokenService;
    private final CommonService commonService;
    private final CategoryRepository categoryRepository;


    // 카테고리 등록
    @Transactional
    public void categoryRegistration(String bearerAccessToken, CategoryRequestDto categoryRequestDto) {
        tokenService.accessTokenExpiration(bearerAccessToken);

        if (!tokenService.findUserRole(bearerAccessToken).equals(Role.ADMIN.getKey())) {
            throw new IllegalStateException(ErrorCode.AUTHORITY_NOT_ADMIN);
        }

        Category category = categoryRequestDto.toCategoryEntity();
        categoryRepository.save(category);
    }

    // API 반환
    @Transactional
    public CommonResponse<Object> categoryRegistrationResponse(String bearerAccessToken, CategoryRequestDto categoryRequestDto) {
        categoryRegistration(bearerAccessToken, categoryRequestDto);

        return commonService.successResponse(SuccessCode.CATEGORY_REGISTRATION_SUCCESS.getDescription(), HttpStatus.CREATED, null);
    }

}
