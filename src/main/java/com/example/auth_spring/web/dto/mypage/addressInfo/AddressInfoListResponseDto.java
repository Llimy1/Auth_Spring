package com.example.auth_spring.web.dto.mypage.addressInfo;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class AddressInfoListResponseDto {

    List<AddressInfoResponseDto> addressInfoResponseDtoList;

    public AddressInfoListResponseDto(List<AddressInfoResponseDto> addressInfoResponseDtoList) {
        this.addressInfoResponseDtoList = addressInfoResponseDtoList;
    }
}
