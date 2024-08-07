package com.just.chatting.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChatRoomDto {
    private Integer chatRoomId;
    private Integer fromUserId;
    private String chatRoomName;
    private List<Integer> toUserIdList;
}
