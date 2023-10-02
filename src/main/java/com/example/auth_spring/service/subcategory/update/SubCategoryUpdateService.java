package com.example.auth_spring.service.subcategory.update;

import com.example.auth_spring.security.jwt.service.TokenService;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.type.ErrorCode;
import com.example.auth_spring.type.Role;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.domain.subcategory.SubCategory;
import com.example.auth_spring.web.domain.subcategory.SubCategoryRepository;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.subcategory.SubCategoryRequestDto;
import com.example.auth_spring.web.dto.subcategory.SubCategoryUpdateResponseDto;
import com.example.auth_spring.web.exception.IllegalStateException;
import com.example.auth_spring.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SubCategoryUpdateService {

    private final TokenService tokenService;
    private final CommonService commonService;

    private final SubCategoryRepository subCategoryRepository;


    @Transactional
    // 서브 카테고리명 수정
    public SubCategoryUpdateResponseDto subCategoryUpdate(String bearerAccessToken, String beforeSubCategoryName, String afterSubCategoryName) {

        tokenService.accessTokenExpiration(bearerAccessToken);

        if (!tokenService.findUserRole(bearerAccessToken).equals(Role.ADMIN.getKey())) {
            throw new IllegalStateException(ErrorCode.AUTHORITY_NOT_ADMIN);
        }

        SubCategory subCategory = subCategoryRepository.findByName(beforeSubCategoryName)
                .orElseThrow(() -> new NotFoundException(ErrorCode.SUB_CATEGORY_NOT_FOUND));

        subCategory.subCategoryUpdate(afterSubCategoryName);

        return SubCategoryUpdateResponseDto.builder()
                .beforeSubCategoryName(beforeSubCategoryName)
                .afterSubCategoryName(afterSubCategoryName)
                .build();
    }


    // API 반환
    @Transactional
    public CommonResponse<Object> subCategoryUpdateResponse(String bearerAccessToken, String beforeSubCategoryName, String afterSubCategoryName) {

        return commonService.successResponse(SuccessCode.SUB_CATEGORY_UPDATE_SUCCESS.getDescription(), HttpStatus.OK, subCategoryUpdate(bearerAccessToken, beforeSubCategoryName, afterSubCategoryName));

    }

}
