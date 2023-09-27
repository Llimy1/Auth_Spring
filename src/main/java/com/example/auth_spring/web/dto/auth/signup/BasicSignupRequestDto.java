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
public class BasicSignupRequestDto {
    @ApiModelProperty(name = "email", value = "email", example = "abcd@naver.com")
    private String email;
    @ApiModelProperty(name = "password", value = "password", example = "abcd1234")
    private String password;
    @ApiModelProperty(name = "name", value = "name", example = "홍길동")
    private String name;
    @ApiModelProperty(name = "nickname", value = "nickname", example = "바람")
    private String nickname;
    @ApiModelProperty(name = "phoneNumber", value = "phoneNumber", example = "01000000000")
    private String phoneNumber;
    @ApiModelProperty(name = "gender", value = "gender", example = "male")
    private String gender;
    @ApiModelProperty(name = "profileImgUrl", value = "imgUrl", example = "imgUrl")
    private String profileImgUrl;
    @ApiModelProperty(name = "introduce", value = "introduce", example = "안녕하세요")
    private String introduce;
    @ApiModelProperty(name = "provider", value = "provider", example = "kakao")
    private String provider;
    @ApiModelProperty(name = "zipCode", value = "zipCode", example = "00012")
    private String zipCode;
    @ApiModelProperty(name = "streetAddress", value = "streetAddress", example = "서울시 강남구")
    private String streetAddress;
    @ApiModelProperty(name = "detailAddress", value = "detailAddress", example = "블라동 10")
    private String detailAddress;

    @Builder
    public BasicSignupRequestDto(String email, String password, String name,
                                 String nickname, String phoneNumber, String gender,
                                 String profileImgUrl, String introduce, String zipCode,
                                 String streetAddress, String detailAddress, String provider) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.profileImgUrl = profileImgUrl;
        this.introduce = introduce;
        this.zipCode = zipCode;
        this.streetAddress = streetAddress;
        this.detailAddress = detailAddress;
        this.provider = provider;
    }


    public User toBasicUserEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .nickname(nickname)
                .phoneNumber(phoneNumber)
                .gender(gender)
                .profileImgUrl(profileImgUrl)
                .introduce(introduce)
                .role(Role.USER)
                .provider(null)
                .build();
    }

    public Address toAddressEntity(User user) {
        return Address.builder()
                .zipCode(zipCode)
                .streetAddress(streetAddress)
                .detailAddress(detailAddress)
                .user(user)
                .isDefault(true)
                .build();
    }
}
