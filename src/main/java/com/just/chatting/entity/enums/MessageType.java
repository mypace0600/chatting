package com.just.chatting.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MessageType {
    CHAT("CHAT","CHAT"),
    ENTER("ENTER","ENTER"),
    LEAVE("LEAVE","LEAVE");

    private final String key;
    private final String title;
}
