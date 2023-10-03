package com.example.auth_spring.service.user.mypage.addressinfo;

import com.example.auth_spring.service.user.token.TokenService;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.mypage.addressInfo.AddressInfoListResponseDto;
import com.example.auth_spring.web.dto.mypage.addressInfo.AddressInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AddressInfoService {

    private final TokenService tokenService;
    private final CommonService commonService;


    // 내 주소 정보 조회
    public AddressInfoListResponseDto addressInfo(String bearerAccessToken) {

        List<AddressInfoResponseDto> addressInfoResponseDtoList = tokenService.findAllAddress(bearerAccessToken);

        return new AddressInfoListResponseDto(addressInfoResponseDtoList);
    }


    // API 반환
    public CommonResponse<Object> addressInfoResponse(String bearerAccessToken) {
        return commonService.successResponse(SuccessCode.ADDRESS_INFO_CHECK_SUCCESS.getDescription(), HttpStatus.OK, addressInfo(bearerAccessToken));
    }

}
