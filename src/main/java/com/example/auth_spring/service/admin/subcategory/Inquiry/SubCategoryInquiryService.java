package com.example.auth_spring.service.admin.subcategory.Inquiry;

import com.example.auth_spring.service.user.token.TokenService;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.type.ErrorCode;
import com.example.auth_spring.type.Role;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.domain.category.Category;
import com.example.auth_spring.web.domain.category.CategoryRepository;
import com.example.auth_spring.web.domain.subcategory.SubCategoryRepository;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.subcategory.SubCategoryListResponseDto;
import com.example.auth_spring.web.dto.subcategory.SubCategoryResponseDto;
import com.example.auth_spring.web.exception.IllegalStateException;
import com.example.auth_spring.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SubCategoryInquiryService {

    private final TokenService tokenService;
    private final CommonService commonService;

    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;


    // 카테고리 내부의 서브 카테고리 조회
    public SubCategoryListResponseDto subCategoryList(String bearerAccessToken,
                                                      String categoryName) {

        tokenService.accessTokenExpiration(bearerAccessToken);

        if (!tokenService.findUserRole(bearerAccessToken).equals(Role.ADMIN.getKey())) {
            throw new IllegalStateException(ErrorCode.AUTHORITY_NOT_ADMIN);
        }

        Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CATEGORY_NOT_FOUND));

        Long categoryId = category.getId();

        List<SubCategoryResponseDto> subCategoryList = subCategoryRepository.findAllByCategoryId(categoryId)
                .stream()
                .map(SubCategoryResponseDto::new)
                .collect(Collectors.toList());

        return SubCategoryListResponseDto.builder()
                .categoryName(categoryName)
                .subCategoryNameList(subCategoryList)
                .build();
    }

    public CommonResponse<Object> subCategoryListResponse(String bearerAccessToken,
                                                         String categoryName) {
        return commonService.successResponse(SuccessCode.SUB_CATEGORY_INQUIRY_SUCCESS.getDescription(),
                HttpStatus.OK,
                subCategoryList(bearerAccessToken, categoryName));
    }
}
