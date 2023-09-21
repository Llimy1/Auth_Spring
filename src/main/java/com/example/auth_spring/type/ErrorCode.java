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
    CERTIFICATION_CODE_INCONSISTENCY("인증 번호가 일치하지 않습니다."),
    PROVIDER_NOT_FOUND("올바르지 않습니다. 다시 확인해 주세요."),
    ACCESS_TOKEN_EXPIRATION("ACCESS TOKEN이 만료되었습니다."),
    USER_NOT_FOUND("해당 유저를 찾을 수 없습니다."),
    INCORRECT_CLAIM("토큰 정보가 잘못되었습니다."),
    AUTHORITY_NOT_FOUND("권한 정보가 없습니다."),
    LOGIN_EXCEPTION("로그인 실패! 이메일과 비밀번호를 확인해주세요.");



    private final String description;
}
