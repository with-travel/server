package com.arom.with_travel.oauth;

import java.util.Map;

public class KakaoUserInfo extends OAuth2UserInfo {
    public KakaoUserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getName() {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        return (String) properties.get("nickname");
    }

    @Override
    public String getEmail() {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        return kakaoAccount.get("email") != null ? kakaoAccount.get("email").toString() : null;
    }
}
