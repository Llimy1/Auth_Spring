package com.example.auth_spring.service.mypage.myinfo;

import com.example.auth_spring.security.jwt.service.TokenService;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.domain.address.Address;
import com.example.auth_spring.web.domain.user.User;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.mypage.myinfo.MyInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MyInfoService {

    private final TokenService tokenService;
    private final CommonService commonService;

    public MyInfoResponseDto myInfo(String bearAccessToken) {

        User user = tokenService.findUser(bearAccessToken);

        return new MyInfoResponseDto(user);
    }

    public CommonResponse<Object>  myInfoResponse(String bearerAccessToken) {
        return commonService.successResponse(SuccessCode.MY_INFO_CHECK_SUCCESS.getDescription(), HttpStatus.OK
        , myInfo(bearerAccessToken));
    }
}
