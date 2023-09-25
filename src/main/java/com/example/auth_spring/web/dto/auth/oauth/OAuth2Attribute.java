package com.example.auth_spring.web.dto.auth.oauth;

import com.example.auth_spring.type.ErrorCode;
import com.example.auth_spring.type.Role;
import com.example.auth_spring.web.domain.user.User;
import com.example.auth_spring.web.exception.DoNotSearchProviderException;
import lombok.Builder;
import lombok.Getter;


import java.util.HashMap;
import java.util.Map;

@Getter
public class OAuth2Attribute {
    private Map<String, Object> attributes;
    private String email;
    private String name;
    private String profileImgUrl;
    private String provider;
    private String nameAttributeKey;

    @Builder
    public OAuth2Attribute(Map<String, Object> attributes, String email, String name, String profileImgUrl, String provider, String nameAttributeKey) {
        this.attributes = attributes;
        this.email = email;
        this.name = name;
        this.profileImgUrl = profileImgUrl;
        this.provider = provider;
        this.nameAttributeKey = nameAttributeKey;
    }

    public static OAuth2Attribute of(String provider, String userNameAttributeName, Map<String, Object> attributes) {

        switch (provider) {
            case "google":
                return ofGoogle(provider, userNameAttributeName, attributes);
            case "naver":
                return ofNaver(provider, "id", attributes);
            case "kakao":
                return ofKakao(provider, "email", attributes);
            default:
                throw new DoNotSearchProviderException(ErrorCode.DO_NOT_SEARCH_PROVIDER);
        }
    }

    private static OAuth2Attribute ofGoogle(String provider, String userNameAttributeName, Map<String, Object> attributes) {
        return OAuth2Attribute.builder()
                .provider(provider)
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .profileImgUrl((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuth2Attribute ofNaver(String provider, String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuth2Attribute.builder()
                .provider(provider)
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .profileImgUrl((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuth2Attribute ofKakao(String provider, String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");

        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

        return OAuth2Attribute.builder()
                .provider(provider)
                .name((String) kakaoProfile.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .profileImgUrl((String) kakaoProfile.get("profile_image_url"))
                .attributes(kakaoAccount)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public User toOAuth2UserEntity() {
        return User.builder()
                .email(email)
                .name(name)
                .profileImgUrl(profileImgUrl)
                .role(Role.GUEST)
                .provider(provider)
                .build();
    }
}
