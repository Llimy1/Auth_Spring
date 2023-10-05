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
    ACCESS_TOKEN_EXPIRATION("ACCESS TOKEN이 올바르지 않거나, 만료되었습니다. 재발급 해주세요."),
    ACCESS_TOKEN_FIVE_MINUTES("ACCESS TOKEN 만료 시간이 5분 이하 입니다. 재발급 해주세요."),
    USER_NOT_FOUND("해당 유저를 찾을 수 없습니다."),
    LOGIN_INFO_NOT_FOUND("해당 로그인 정보를 찾을 수 없습니다."),
    INCORRECT_CLAIM("토큰 정보가 잘못되었습니다."),
    AUTHORITY_NOT_FOUND("권한 정보가 없습니다."),
    AUTHORITY_NOT_SELLER("판매자 권한이 없습니다."),
    AUTHORITY_NOT_ADMIN("관리자 권한이 없습니다."),
    AUTHORITY_NOT_USER("유저 권한이 없습니다."),
    LOGIN_EXCEPTION("로그인 실패! 이메일과 비밀번호를 확인해주세요."),
    REFRESH_TOKEN_NOT_FOUND("REFRESH TOKEN을 찾을 수 없습니다."),
    REFRESH_TOKEN_EXPIRATION("REFRESH TOKEN이 올바르지 않거나, 만료되었습니다. 재로그인 해주세요."),
    DO_NOT_SEARCH_PROVIDER("해당 소셜 로그인을 지원하지 않습니다."),
    SELLER_PRODUCT_NOT_FOUND("판매자 상품을 찾을 수 없습니다."),
    PRODUCT_NOT_FOUND("판매 상품을 찾을 수 없습니다."),
    CATEGORY_NOT_FOUND("해당 카테고리를 찾을 수 없습니다."),
    SUB_CATEGORY_NOT_FOUND("해당 서브 카테고리를 찾을 수 없습니다."),
    ADDRESS_NOT_FOUND("해당 유저의 주소를 찾을 수 없습니다."),
    DEFAULT_ADDRESS_TO_MANY("기본 주소가 이미 존재합니다."),
    CART_PRODUCT_NOT_FOUND("장바구니에서 상품을 찾을 수 없습니다."),
    PRODUCT_THAT_EXIST("중복된 상품 입니다."),
    SEARCH_NOT_FOUND("검색한 상품을 찾을 수 없습니다."),
    ORDER_NAME_THAT_EXIST("중복된 주문 번호 입니다."),
    BRAND_NOT_FOUND("브랜드를 찾을 수 없습니다.");



    private final String description;
}
