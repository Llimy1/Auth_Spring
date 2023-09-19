package com.example.auth_spring.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    NICKNAME_THAT_EXIST("이미 존재하는 닉네임 입니다."),
    PHONE_NUMBER_THAT_EXIST("이미 존재하는 핸드폰 번호 입니다."),
    EMAIL_THAT_EXIST("이미 존재하는 이메일 입니다."),
    MESSAGING_EXCEPTION("잘못된 처리 입니다. 다시 확인해 주세요."),
    UNSUPPORTED_ENCODING("지원되지 않는 인코딩 입니다."),
    CERTIFICATION_CODE_INCONSISTENCY("인증 번호가 일치하지 않습니다.");



    private final String description;
}
