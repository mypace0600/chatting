package com.just.chatting.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "tb_mail")
public class Mail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 500, unique = false)
    private String emailAddress;

    @Column(nullable = false, length = 200, unique = true)
    private String verifyCode;

    @CreationTimestamp
    private Timestamp createDate;
}
