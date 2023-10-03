package com.example.auth_spring.service.admin.category.delete;

import com.example.auth_spring.service.user.token.TokenService;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.type.ErrorCode;
import com.example.auth_spring.type.Role;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.domain.category.Category;
import com.example.auth_spring.web.domain.category.CategoryRepository;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.exception.IllegalStateException;
import com.example.auth_spring.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CategoryDeleteService {

    private final TokenService tokenService;
    private final CommonService commonService;

    private final CategoryRepository categoryRepository;


    // 카테고리 삭제
    @Transactional
    public void categoryDelete(String bearerAccessToken, String categoryName) {

        tokenService.accessTokenExpiration(bearerAccessToken);

        if (!tokenService.findUserRole(bearerAccessToken).equals(Role.ADMIN.getKey())) {
            throw new IllegalStateException(ErrorCode.AUTHORITY_NOT_ADMIN);
        }

        Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CATEGORY_NOT_FOUND));

        categoryRepository.delete(category);
    }

    // API 반환
    @Transactional
    public CommonResponse<Object> categoryDeleteResponse(String bearerAccessToken, String categoryName) {
        categoryDelete(bearerAccessToken, categoryName);

        return commonService.successResponse(SuccessCode.CATEGORY_DELETE_SUCCESS.getDescription(), HttpStatus.OK, null);
    }

}
