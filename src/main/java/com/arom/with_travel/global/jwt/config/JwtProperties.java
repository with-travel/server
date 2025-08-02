package com.arom.with_travel.global.jwt.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties("jwt")
public class JwtProperties {
    private String issuer;
    private String secretKey;
    private int accessTokenExpireHours;
    private int refreshTokenExpireDays;
    private String refreshCookieName;
}
