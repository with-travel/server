package com.arom.with_travel.domain.redis;

import com.arom.with_travel.domain.chat.;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class RedisPublisher {

    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(ChannelTopic topic, MessageDto message){
        log.info("published topic = {}", topic.getTopic());
        redisTemplate.convertAndSend(topic.getTopic(),message);
    }

}
