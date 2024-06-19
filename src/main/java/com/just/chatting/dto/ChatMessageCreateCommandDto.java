package com.just.chatting.dto;

public record ChatMessageCreateCommandDto(Integer roomId, String content, Integer fromId) {}
