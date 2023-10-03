package com.example.auth_spring.service.admin.category.update;

import com.example.auth_spring.service.user.token.TokenService;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.type.ErrorCode;
import com.example.auth_spring.type.Role;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.domain.category.Category;
import com.example.auth_spring.web.domain.category.CategoryRepository;
import com.example.auth_spring.web.dto.category.CategoryUpdateResponseDto;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.exception.IllegalStateException;
import com.example.auth_spring.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CategoryUpdateService {

    private final TokenService tokenService;
    private final CommonService commonService;

    private final CategoryRepository categoryRepository;

    // 카테고리명 수정
    @Transactional
    public CategoryUpdateResponseDto categoryUpdate(String bearerAccessToken, String beforeCategoryName, String afterCategoryName) {

        tokenService.accessTokenExpiration(bearerAccessToken);

        if (!tokenService.findUserRole(bearerAccessToken).equals(Role.ADMIN.getKey())) {
            throw new IllegalStateException(ErrorCode.AUTHORITY_NOT_ADMIN);
        }

        Category category = categoryRepository.findByName(beforeCategoryName)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CATEGORY_NOT_FOUND));


        category.categoryUpdate(afterCategoryName);

        return CategoryUpdateResponseDto.builder()
                .beforeCategoryName(beforeCategoryName)
                .afterCategoryName(afterCategoryName)
                .build();
    }

    // API 반환
    @Transactional
    public CommonResponse<Object> categoryUpdateResponse(String bearerAccessToken, String beforeCategoryName, String afterCategoryName) {
        return commonService.successResponse(SuccessCode.CATEGORY_UPDATE_SUCCESS.getDescription(), HttpStatus.OK, categoryUpdate(bearerAccessToken, beforeCategoryName, afterCategoryName));
    }
}
