package com.just.chatting.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.just.chatting.entity.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder.Default;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;

    private String password;

    private String nickName;

    @OneToMany(mappedBy = "fromUser", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Friend> friendsSent;

    @OneToMany(mappedBy = "toUser", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Friend> friendsReceived;

    private String roleType;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ChatRoomUser> chatRoomUserList = new ArrayList<>();

    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ChatMessage> chatMessageList = new ArrayList<>();

    // 생성자 추가
    public User(String email, String password, String nickName, String roleType) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.roleType = roleType;
    }

    public static User createUser(User user, BCryptPasswordEncoder passwordEncoder) {
        return new User(
                user.getEmail(),
                passwordEncoder.encode(user.getPassword()),
                user.getNickName(),
                RoleType.USER.getKey()
        );
    }
}