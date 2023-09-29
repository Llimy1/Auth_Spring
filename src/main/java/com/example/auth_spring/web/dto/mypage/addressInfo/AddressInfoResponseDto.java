package com.example.auth_spring.web.dto.mypage.addressInfo;

import com.example.auth_spring.web.domain.address.Address;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddressInfoResponseDto {

    @ApiModelProperty(example = "우편 번호")
    private String zipCode;
    @ApiModelProperty(example = "도로 주소명")
    private String streetAddress;
    @ApiModelProperty(example = "상세 주소명")
    private String detailAddress;


    @Builder
    public AddressInfoResponseDto(Address address) {
        this.zipCode = address.getZipCode();
        this.streetAddress = address.getStreetAddress();
        this.detailAddress = address.getDetailAddress();
    }
}
