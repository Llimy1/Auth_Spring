package com.example.auth_spring.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessCode {

    SIGNUP_SUCCESS("회원가입에 성공 했습니다."),
    CERTIFICATION_SEND_SUCCESS("인증 코드 전송에 성공 했습니다."),
    CERTIFICATION_CHECK("인증 코드가 일치 합니다."),
    BASIC_LOGIN_SUCCESS("로그인에 성공했습니다.");


    private final String description;

}
