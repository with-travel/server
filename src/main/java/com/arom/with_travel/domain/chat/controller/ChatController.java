package com.arom.with_travel.domain.chat.controller;

import com.arom.with_travel.domain.chat.dto.ChatRequest;
import com.arom.with_travel.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Controller
@Slf4j
public class ChatController {

    @Autowired
    private final ChatService chatService;

    @MessageMapping("/chat/message")
    public void message(ChatRequest.MessageDto messageDto) {
        log.info("enger messageMapping in chat contorller");
        chatService.sendMessage(messageDto);
    }

}
