package com.just.chatting.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@Table(name="t_schedule_log")
public class ScheduleLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String resultCode;
    private String errorMessage;
    private String message;
    private LocalDateTime startDt;
    private LocalDateTime endDt;
    private String ifTy;
    private String serverIp;
}
