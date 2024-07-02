package com.just.chatting.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDto {
    private Integer senderId;
    private Integer chatRoomId;
    private String message;
}
