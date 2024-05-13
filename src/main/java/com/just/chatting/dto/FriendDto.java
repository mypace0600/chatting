package com.just.chatting.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendDto {

    private int id;

    private int fromUserId;

    private int toUserId;
}
