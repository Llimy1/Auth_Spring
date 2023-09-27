package com.example.auth_spring.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessCode {

    BASIC_SIGNUP_SUCCESS("자체 회원가입에 성공 했습니다."),
    OAUTH2_SIGNUP_SUCCESS("소셜 회원가입에 성공 했습니다."),
    CERTIFICATION_SEND_SUCCESS("인증 코드 전송에 성공 했습니다."),
    CERTIFICATION_CHECK("인증 코드가 일치 합니다."),
    BASIC_LOGIN_SUCCESS(" 자체 로그인에 성공했습니다."),
    BASIC_LOGOUT_SUCCESS("로그아웃에 성공했습니다."),
    WITHDRAWAL_SUCCESS("회원탈퇴에 성공했습니다."),
    TOKEN_REISSUE_SUCCESS("토큰 재발급에 성공했습니다."),
    OAUTH2_LOGIN_SUCCESS("소셜 로그인에 성공했습니다."),
    SELLER_CONVERSION_SUCCESS("판매자 전환에 성공했습니다.");

    private final String description;

}
