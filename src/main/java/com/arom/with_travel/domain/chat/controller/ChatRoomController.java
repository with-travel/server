package com.arom.with_travel.domain.chat.controller;


import com.arom.with_travel.domain.chat.dto.ChatroomResponse;
import com.arom.with_travel.domain.chat.service.ChatService;
import com.arom.with_travel.domain.chat.service.ChatroomServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/v1/chat")
public class ChatRoomController {

    private final ChatroomServiceImpl chatService;

    //html 테스트
    @GetMapping("/room")
    public String rooms(Model model){
        return "room";
    }

    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId){
        model.addAttribute("roomId",roomId);
        return "roomdetail.html";
    }

    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatroomResponse.ChatroomDto> rooms() {
        return chatService.findAllRoom();
    }

    @PostMapping("/room")
    @ResponseBody
//    public ChatroomResponse.ChatroomDto createRoom(@RequestParam ChatroomRequest.ChatroomDto chatroom){
//        return chatService.createRoom(chatroom);
    public ChatroomResponse.ChatroomDto createRoom(@RequestParam String name){
        return chatService.saveChatroom(name);
        //        return chatService.createRoom(name);
    }

    @GetMapping("/room/{roomId}")
    @ResponseBody
//    public ChatroomResponse.ChatroomDto roomInifo(@PathVariable ChatroomRequest.ChatroomDto chatroom){
//        return chatService.roomInfo(chatroom);
    public ChatroomResponse.ChatroomDto roomInifo(@PathVariable String roomId){
        return chatService.roomInfo(roomId);
    }

}