package com.just.chatting.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="t_schedule_lock")
public class ScheduleLock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String scheduleName;
    private String scheduleCl;
    private String serverIp;
    private LocalDateTime registeredDate;
}
