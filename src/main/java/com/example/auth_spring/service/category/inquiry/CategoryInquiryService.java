package com.example.auth_spring.service.category.inquiry;


import com.example.auth_spring.security.jwt.service.TokenService;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.type.ErrorCode;
import com.example.auth_spring.type.Role;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.domain.category.Category;
import com.example.auth_spring.web.domain.category.CategoryRepository;
import com.example.auth_spring.web.dto.category.CategoryListResponseDto;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.exception.IllegalStateException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryInquiryService {

    private final TokenService tokenService;
    private final CommonService commonService;
    private final CategoryRepository categoryRepository;

    // 카테고리 조회
    public CategoryListResponseDto categoryList(String bearerAccessToken) {
        tokenService.accessTokenExpiration(bearerAccessToken);

        if (!tokenService.findUserRole(bearerAccessToken).equals(Role.ADMIN.getKey())) {
            throw new IllegalStateException(ErrorCode.AUTHORITY_NOT_ADMIN);
        }

        List<Category> categoryList = categoryRepository.findAll();

        return CategoryListResponseDto.builder()
                .categoryList(categoryList)
                .build();
    }

    // API 반환
    public CommonResponse<Object> categoryListResponse(String bearerAccessToken) {
        return commonService.successResponse(SuccessCode.CATEGORY_INQUIRY_SUCCESS.getDescription(), HttpStatus.OK, categoryList(bearerAccessToken));
    }

}
