package com.just.chatting.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ChatMessageDto {
    private Integer senderId;
    private Integer chatRoomId;
    private String message;
    private Timestamp sendDt;
}
