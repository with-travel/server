package com.arom.with_travel.domain.chat;

import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chatroom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean status = Boolean.FALSE;

    @NotNull private String roomName;

    @OneToMany(mappedBy = "chatroom")
    private List<Chat> chats = new ArrayList<>();

    @OneToMany(mappedBy = "chatroom")
    private List<ChatPart> chatParts = new ArrayList<>();
}
