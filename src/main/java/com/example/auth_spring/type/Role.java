package com.example.auth_spring.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    GUEST("ROLE_GUEST", "첫 소셜 로그인 유저"),
    USER("ROLE_USER", "일반 사용자"),
    SELLER("ROLE_SELLER", "판매자"),
    ADMIN("ROLE_ADMIN", "관리자");

    private final String key;
    private final String title;
}
