package com.just.chatting.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_chat_room")
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(mappedBy = "chatRoom", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ChatRoomUser> chatRoomUserList = new ArrayList<>();

    public ChatRoom(String name) {
        this.name = name;
    }

    public static ChatRoom create(String name) {
        return new ChatRoom(name);
    }

    @CreatedDate
    private Timestamp createdDt;
}

