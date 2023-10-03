package com.example.auth_spring.service.admin.subcategory.delete;

import com.example.auth_spring.service.user.token.TokenService;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.type.ErrorCode;
import com.example.auth_spring.type.Role;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.domain.subcategory.SubCategory;
import com.example.auth_spring.web.domain.subcategory.SubCategoryRepository;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SubCategoryDeleteService {

    private final TokenService tokenService;
    private final CommonService commonService;

    private final SubCategoryRepository subCategoryRepository;

    @Transactional
    // 서브 카테고리 삭제
    public void subCategoryDelete(String bearerAccessToken, String subCategoryName) {

        tokenService.accessTokenExpiration(bearerAccessToken);

        if (tokenService.findUserRole(bearerAccessToken).equals(Role.ADMIN.getKey())) {
            throw new NotFoundException(ErrorCode.AUTHORITY_NOT_ADMIN);
        }

        SubCategory subCategory = subCategoryRepository.findByName(subCategoryName)
                        .orElseThrow(() -> new NotFoundException(ErrorCode.SUB_CATEGORY_NOT_FOUND));

        subCategoryRepository.delete(subCategory);
    }

    @Transactional
    // API 반환
    public CommonResponse<Object> subCategoryDeleteResponse(String bearerAccessToken, String subCategoryName) {
        subCategoryDelete(bearerAccessToken, subCategoryName);

        return commonService.successResponse(SuccessCode.SUB_CATEGORY_DELETE_SUCCESS.getDescription(), HttpStatus.OK, null);
    }




}
