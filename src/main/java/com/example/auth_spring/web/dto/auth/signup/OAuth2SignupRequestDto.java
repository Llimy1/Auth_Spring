package com.example.auth_spring.web.dto.auth.signup;

import com.example.auth_spring.type.Role;
import com.example.auth_spring.web.domain.address.Address;
import com.example.auth_spring.web.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OAuth2SignupRequestDto {
    private String email;
    private String nickname;
    private String phoneNumber;
    private String gender;
    private String introduce;
    private String streetAddress;
    private String detailAddress;

    @Builder
    public OAuth2SignupRequestDto(String email, String nickname, String phoneNumber, String gender, String introduce, String streetAddress, String detailAddress) {
        this.email = email;
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
