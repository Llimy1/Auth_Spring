package com.example.auth_spring.service.mypage.addressinfo;

import com.example.auth_spring.security.jwt.service.TokenService;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.domain.address.Address;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.mypage.addressInfo.AddressInfoListResponseDto;
import com.example.auth_spring.web.dto.mypage.addressInfo.AddressInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AddressInfoService {

    private final TokenService tokenService;
    private final CommonService commonService;

    public AddressInfoListResponseDto addressInfo(String bearerAccessToken) {

        List<AddressInfoResponseDto> addressInfoResponseDtoList = tokenService.findAllAddress(bearerAccessToken);


        return new AddressInfoListResponseDto(addressInfoResponseDtoList);
    }

    public CommonResponse<Object> addressInfoResponse(String bearerAccessToken) {
        return commonService.successResponse(SuccessCode.ADDRESS_INFO_CHECK_SUCCESS.getDescription(), HttpStatus.OK, addressInfo(bearerAccessToken));
    }

}
