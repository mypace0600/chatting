package com.just.chatting.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Getter
@Setter
@Table(name = "tb_chat_message_read_status")
@EntityListeners(AuditingEntityListener.class)
public class ChatMessageReadStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_message_id")
    private ChatMessage chatMessage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private boolean isRead;

    public ChatMessageReadStatus(ChatMessage chatMessage, User user, boolean isRead) {
        this.chatMessage = chatMessage;
        this.user = user;
        this.isRead = isRead;
    }
}

