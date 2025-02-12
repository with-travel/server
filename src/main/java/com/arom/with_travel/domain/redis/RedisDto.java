package com.arom.with_travel.domain.redis;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;

@Getter
@Setter
@NoArgsConstructor
public class RedisDto {
    private String key;
    private String value;
    private Duration duration;
}
