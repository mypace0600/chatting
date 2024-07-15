package com.just.chatting.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;

@Document(collection = "mongo_entity")
@Entity
@Getter
@Setter
@Table(name = "tb_chat_message")
@EntityListeners(AuditingEntityListener.class)
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "chat_room_id",  referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id", nullable = false)
    private User sender;

    private String content;

    private String messageType;

    @CreatedDate
    private Timestamp sendDt;
}

