package com.example.auth_spring.service.user.mypage.myinfo;

import com.example.auth_spring.security.jwt.service.JwtProvider;
import com.example.auth_spring.service.user.token.TokenService;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.type.ErrorCode;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.domain.user.User;
import com.example.auth_spring.web.domain.user.UserRepository;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.mypage.myinfo.MyInfoResponseDto;
import com.example.auth_spring.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MyInfoService {


    private final TokenService tokenService;
    private final CommonService commonService;
    private final UserRepository userRepository;

    // 내 정보 조회
    public MyInfoResponseDto myInfo(String bearAccessToken) {

        tokenService.accessTokenExpiration(bearAccessToken);

        String email = tokenService.accessTokenEmail(bearAccessToken);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        return new MyInfoResponseDto(user);
    }

    // API 반환
    public CommonResponse<Object>  myInfoResponse(String bearerAccessToken) {
        return commonService.successResponse(SuccessCode.MY_INFO_CHECK_SUCCESS.getDescription(), HttpStatus.OK
        , myInfo(bearerAccessToken));
    }
}
