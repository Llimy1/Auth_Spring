package com.example.auth_spring.web.dto;

import com.example.auth_spring.web.domain.address.Address;
import com.example.auth_spring.web.domain.role.Role;
import com.example.auth_spring.web.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupResponseDto {

    private Long userid;
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String phoneNumber;
    private String gender;
    private String profileImgUrl;
    private String introduce;
    private Role role;
    private String mainAddress;
    private String detailAddress;

    public SignupResponseDto(User user, Address address) {
        this.userid = address.getUser().getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.phoneNumber = user.getPhoneNumber();
        this.gender = user.getGender();
        this.profileImgUrl = user.getProfileImgUrl();
        this.introduce = user.getIntroduce();
        this.role = user.getRole();
        this.mainAddress = address.getMainAddress();
        this.detailAddress = address.getDetailAddress();
    }
}
