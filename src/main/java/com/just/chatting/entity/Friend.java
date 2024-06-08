package com.just.chatting.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friend_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "from_user_id", referencedColumnName = "id", nullable = false)
    private User fromUser;

    @ManyToOne
    @JoinColumn(name = "to_user_id", referencedColumnName = "id", nullable = false)
    private User toUser;

    @Column(name = "are_we_friend")
    private boolean areWeFriend;
}
