package com.example.auth_spring.service.user.cart.inquiry;

import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.service.user.token.TokenService;
import com.example.auth_spring.type.ErrorCode;
import com.example.auth_spring.type.Role;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.domain.cart.CartRepository;
import com.example.auth_spring.web.domain.user.User;
import com.example.auth_spring.web.dto.cart.CartListResponseDto;
import com.example.auth_spring.web.dto.cart.CartResponseDto;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.Pagination;
import com.example.auth_spring.web.exception.IllegalStateException;
import com.example.auth_spring.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CartInquiryService {

    private final TokenService tokenService;
    private final CommonService commonService;
    private final CartRepository cartRepository;


    // 장바구니에 등록한 시간이 가장 최근순으로 반환
    public CartListResponseDto cartInquiry(String bearerAccessToken, int page, int size, String sortBy) {

        tokenService.accessTokenExpiration(bearerAccessToken);

        User user = tokenService.findUser(bearerAccessToken);

        if (!user.getRole().equals(Role.USER)) {
            throw new IllegalStateException(ErrorCode.AUTHORITY_NOT_USER);
        }

        Long userId = user.getId();

        PageRequest pageable = PageRequest.of(page - 1, size, Sort.by(sortBy).descending());

        Page<CartResponseDto> data = cartRepository.findAllByUserId(userId, pageable).map(CartResponseDto::new);

        if (data.isEmpty()) {
            throw new NotFoundException(ErrorCode.CART_PRODUCT_NOT_FOUND);
        }

        Pagination pagination = Pagination.builder()
                .totalPages(data.getTotalPages())
                .totalElements(data.getTotalElements())
                .pageNo(data.getNumber())
                .isLastPage(data.isLast())
                .build();

        return CartListResponseDto.builder()
                .cartList(data.getContent())
                .pagination(pagination)
                .build();
    }


    // API 반환
    public CommonResponse<Object> cartInquiryResponse(String bearerAccessToken, int page, int size, String sortBy) {
        return commonService.successResponse(SuccessCode.CART_INQUIRY_SUCCESS.getDescription(), HttpStatus.OK, cartInquiry(bearerAccessToken, page, size, sortBy));
    }
}