package com.arom.with_travel.global.oauth2.dto;

import lombok.extern.slf4j.Slf4j;
import java.util.Map;

// 차현철
@Slf4j
public class KakaoResponse implements OAuth2Response {

    private final String oauthId;
    private final Map<String, Object> attribute;

    public KakaoResponse(String oauthId, Map<String, Object> attribute) {
        this.oauthId = oauthId;
        this.attribute = (Map<String, Object>) attribute.get("kakao_account");
    }

    @Override
    public Map<String, Object> getAttribute() {
        return attribute;
    }

    @Override
    public String getOauthId() {
        return oauthId;
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getEmail() {
        if (attribute == null) {
            return null;
        }
        Object email = attribute.get("email");
        return email != null ? email.toString() : null;
    }

    @Override
    public String getName() {
        if (attribute == null) {
            return null;
        }
        Map<String, Object> profile = (Map<String, Object>) attribute.get("profile");
        if (profile != null && profile.get("nickname") != null) {
            return profile.get("nickname").toString();
        }
        return null;
    }
}
