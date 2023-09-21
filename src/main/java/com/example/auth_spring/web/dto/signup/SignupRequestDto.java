package com.example.auth_spring.web.dto.signup;

import com.example.auth_spring.security.config.PasswordEncoderConfig;
import com.example.auth_spring.type.Role;
import com.example.auth_spring.web.domain.address.Address;
import com.example.auth_spring.web.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class SignupRequestDto {

    private String email;
    private String password;
    private String name;
    private String nickname;
    private String phoneNumber;
    private String gender;
    private String profileImgUrl;
    private String introduce;
    private String provider;
    private String zipCode;
    private String streetAddress;
    private String detailAddress;

    @Builder
    public SignupRequestDto(String email, String password, String name,
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


    public User toUserEntity() {
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
