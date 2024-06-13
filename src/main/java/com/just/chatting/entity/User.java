package com.just.chatting.entity;

import com.just.chatting.entity.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;

    private String password;

    private String nickName;

    @OneToMany(mappedBy = "fromUser", fetch = FetchType.LAZY)
    private List<Friend> friendsSent;

    @OneToMany(mappedBy = "toUser", fetch = FetchType.LAZY)
    private List<Friend> friendsReceived;

    private String roleType;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<ChatRoomUser> chatRoomUserList = new ArrayList<>();

    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    private List<ChatMessage> chatMessageList = new ArrayList<>();

    public static User createUser(User user, BCryptPasswordEncoder passwordEncoder) {
        return User.builder()
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .roleType(RoleType.USER.getKey())
                .nickName(user.getNickName())
                .build();
    }
}
