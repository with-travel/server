package com.arom.with_travel.domain.community.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum CommunityTag {
    RESTAURANT("맛집추천"),
    CAFE("카페탐방"),
    INFO("정보공유"),
    TIPS("꿀팁");

    private final String labelKo;

    CommunityTag(String labelKo) {
        this.labelKo = labelKo;
    }

    public String getLabelKo() {
        return labelKo;
    }

    @JsonCreator
    public static CommunityTag from(String v) {
        if (v == null) return null;
        String key = v.trim();
        String upper = key.toUpperCase();

        return Arrays.stream(values())
                .filter(e -> e.name().equalsIgnoreCase(upper) || e.labelKo.equalsIgnoreCase(key))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown tag: " + v));
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}