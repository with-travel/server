package com.arom.with_travel.domain.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sejong.project.testdocker.chatCustom.dto.ChatRequest.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class RedisSubscriber implements MessageListener {
    private final ObjectMapper objectMapper;
    private final RedisTemplate redisTemplate;
    private final SimpMessageSendingOperations messagingTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern){
        try{
            String publishMessage = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());
            log.info("Received message:{}", publishMessage);

            MessageDto roomMessage = objectMapper.readValue(publishMessage, MessageDto.class);
            log.info("Deserialized message: {}", roomMessage.message());

            messagingTemplate.convertAndSend("/sub/chat/room/"+roomMessage.roomId(),roomMessage);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }
}
