package com.example.auth_spring.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessCode {

    SIGNUP_SUCCESS("회원가입에 성공 했습니다."),
    CERTIFICATION_SEND_SUCCESS("인증 코드 전송에 성공 했습니다."),
    CERTIFICATION_CHECK("인증 코드가 일치 합니다."),
    BASIC_LOGIN_SUCCESS("로그인에 성공했습니다."),
    BASIC_LOGOUT_SUCCESS("로그아웃에 성공했습니다."),
    WITHDRAWAL_SUCCESS("회원탈퇴에 성공했습니다."),
    TOKEN_REISSUE_SUCCESS("토큰 재발급에 성공했습니다.");

    private final String description;

}
