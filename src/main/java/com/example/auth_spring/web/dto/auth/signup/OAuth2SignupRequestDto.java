package com.example.auth_spring.web.dto.auth.signup;

import com.example.auth_spring.type.Role;
import com.example.auth_spring.web.domain.address.Address;
import com.example.auth_spring.web.domain.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OAuth2SignupRequestDto {
    @ApiModelProperty(name = "password", value = "password", example = "abcd1234")
    private String nickname;
    @ApiModelProperty(name = "phoneNumber", value = "phoneNumber", example = "01000000000")
    private String phoneNumber;
    @ApiModelProperty(name = "gender", value = "gender", example = "male")
    private String gender;
    @ApiModelProperty(name = "introduce", value = "introduce", example = "안녕하세요")
    private String introduce;
    @ApiModelProperty(name = "zipCode", value = "zipCode", example = "00012")
    private String zipCode;
    @ApiModelProperty(name = "streetAddress", value = "streetAddress", example = "서울시 강남구")
    private String streetAddress;
    @ApiModelProperty(name = "detailAddress", value = "detailAddress", example = "블라동 10")
    private String detailAddress;

    @Builder
    public OAuth2SignupRequestDto(String nickname, String phoneNumber, String gender, String introduce, String streetAddress, String detailAddress) {
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.introduce = introduce;
        this.streetAddress = streetAddress;
        this.detailAddress = detailAddress;
    }


    public Address toOAuth2AddressEntity(User user) {
        return Address.builder()
                .streetAddress(streetAddress)
                .detailAddress(detailAddress)
                .user(user)
                .isDefault(true)
                .build();
    }
}
