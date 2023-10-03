package com.example.auth_spring.service.admin.subcategory.registration;

import com.example.auth_spring.service.user.token.TokenService;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.type.ErrorCode;
import com.example.auth_spring.type.Role;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.domain.category.Category;
import com.example.auth_spring.web.domain.category.CategoryRepository;
import com.example.auth_spring.web.domain.subcategory.SubCategory;
import com.example.auth_spring.web.domain.subcategory.SubCategoryRepository;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.subcategory.SubCategoryRequestDto;
import com.example.auth_spring.web.exception.IllegalStateException;
import com.example.auth_spring.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SubCategoryRegistrationService {

    private final TokenService tokenService;
    private final CommonService commonService;

    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;


    @Transactional
    // 서브 카테고리 저장
    public void subCategoryRegistration(String bearerAccessToken, String categoryName, SubCategoryRequestDto subCategoryRequestDto) {

        tokenService.accessTokenExpiration(bearerAccessToken);

        if (!tokenService.findUserRole(bearerAccessToken).equals(Role.ADMIN.getKey())) {
            throw new IllegalStateException(ErrorCode.AUTHORITY_NOT_ADMIN);
        }

        Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CATEGORY_NOT_FOUND));

        SubCategory subCategory = subCategoryRequestDto.toSubCategoryEntity(category);

        subCategoryRepository.save(subCategory);
    }

    @Transactional
    // API 반환
    public CommonResponse<Object> subCategoryRegistrationResponse(String bearerAccessToken, String categoryName, SubCategoryRequestDto subCategoryRequestDto) {
        subCategoryRegistration(bearerAccessToken, categoryName, subCategoryRequestDto);

        return commonService.successResponse(SuccessCode.SUB_CATEGORY_REGISTRATION_SUCCESS.getDescription(), HttpStatus.CREATED, null);
    }



}
