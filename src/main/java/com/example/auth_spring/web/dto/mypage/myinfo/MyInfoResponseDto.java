package com.example.auth_spring.web.dto.mypage.myinfo;

import com.example.auth_spring.web.domain.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MyInfoResponseDto {
    @ApiModelProperty(example = "이메일")
    private String email;
    @ApiModelProperty(example = "이름")
    private String name;
    @ApiModelProperty(example = "닉네임")
    private String nickname;
    @ApiModelProperty(example = "휴대폰 번호")
    private String phoneNumber;
    @ApiModelProperty(example = "성별")
    private String gender;
    @ApiModelProperty(example = "이미지 URL")
    private String profileImgUrl;
    @ApiModelProperty(example = "간단 소개글")
    private String introduce;



    @Builder
    public MyInfoResponseDto(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.phoneNumber = user.getPhoneNumber();
        this.gender = user.getGender();
        this.profileImgUrl = user.getProfileImgUrl();
        this.introduce = user.getIntroduce();
    }
}
