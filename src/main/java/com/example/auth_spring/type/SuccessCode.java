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
    BASIC_LOGIN_SUCCESS(" 자체 로그인에 성공 했습니다."),
    BASIC_LOGOUT_SUCCESS("로그아웃에 성공 했습니다."),
    WITHDRAWAL_SUCCESS("회원탈퇴에 성공 했습니다."),
    TOKEN_REISSUE_SUCCESS("토큰 재발급에 성공 했습니다."),
    OAUTH2_LOGIN_SUCCESS("소셜 로그인에 성공 했습니다."),
    SELLER_CONVERSION_SUCCESS("판매자 전환에 성공 했습니다."),
    MY_INFO_CHECK_SUCCESS("내 정보 조회에 성공 했습니다."),
    ADDRESS_INFO_CHECK_SUCCESS("내 주소 조회에 성공 했습니다."),
    PRODUCT_REGISTRATION_SUCCESS("상품 등록에 성공 했습니다."),
    SELLER_PRODUCT_INQUIRY_SUCCESS("판매자 상품 조회에 성공 했습니다."),
    NICKNAME_USE_EXIST("사용 가능한 닉네임 입니다."),
    EMAIL_USE_EXIST("사용 가능한 이메일 입니다."),
    PHONE_NUMBER_USE_EXIST("사용 가능한 번호 입니다."),
    CATEGORY_REGISTRATION_SUCCESS("카테고리 등록에 성공 했습니다."),
    CATEGORY_INQUIRY_SUCCESS("카테고리 조회에 성공 했습니다."),
    CATEGORY_DELETE_SUCCESS("카테고리 삭제에 성공 했습니다."),
    CATEGORY_UPDATE_SUCCESS("카테고리 변경에 성공 했습니다."),
    SUB_CATEGORY_REGISTRATION_SUCCESS("서브 카테고리 등록에 성공 했습니다."),
    SUB_CATEGORY_INQUIRY_SUCCESS("서브 카테고리 조회에 성공 했습니다."),
    SUB_CATEGORY_DELETE_SUCCESS("서브 카테고리 삭제에 성공 했습니다."),
    SUB_CATEGORY_UPDATE_SUCCESS("서브 카테고리 변경에 성공 했습니다."),
    ADDRESS_UPDATE_SUCCESS("주소 변경에 성공 했습니다."),
    ALL_PRODUCT_INQUIRY_SUCCESS("전체 상품 조회에 성공 했습니다."),
    CART_REGISTRATION_SUCCESS("장바구니에 상품을 등록 했습니다."),
    CART_INQUIRY_SUCCESS("장바구니에 상품 조회에 성공 했습니다."),
    CART_DELETE_SUCCESS("장바구니 상품 삭제에 성공 했습니다.");

    private final String description;

}
