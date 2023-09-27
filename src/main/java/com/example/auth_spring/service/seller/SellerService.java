package com.example.auth_spring.service.seller;

import com.example.auth_spring.security.jwt.service.JwtProvider;
import com.example.auth_spring.security.jwt.service.TokenService;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.domain.user.User;
import com.example.auth_spring.web.domain.user.UserRepository;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.UserIdResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SellerService {

    private final CommonService commonService;
    private final TokenService tokenService;


    // 판매자 전환
    @Transactional
    public Long conversion(String bearerAccessToken) {
        tokenService.accessTokenExpiration(bearerAccessToken);

        User user = tokenService.findUser(bearerAccessToken);

        user.roleUpdate();

        return user.getId();
    }

    // API 반환
    @Transactional
    public CommonResponse<Object> conversionResponse(String bearerAccessToken) {
        Long userId = conversion(bearerAccessToken);

        return commonService.successResponse(SuccessCode.SELLER_CONVERSION_SUCCESS.getDescription(), HttpStatus.OK, new UserIdResponseDto(userId));
    }

}
