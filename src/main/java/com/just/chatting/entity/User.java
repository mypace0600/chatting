package com.just.chatting.entity;

import com.just.chatting.entity.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

    @OneToMany(mappedBy = "fromUser")
    private List<Friend> friendsSent;

    @OneToMany(mappedBy = "toUser")
    private List<Friend> friendsReceived;

    private String roleType;

    public static User createUser(User user, BCryptPasswordEncoder passwordEncoder) {
        return User.builder()
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .roleType(RoleType.ADMIN.getKey())
                .build();
    }
}
