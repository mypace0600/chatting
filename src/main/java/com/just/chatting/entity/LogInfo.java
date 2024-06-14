package com.just.chatting.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="t_log_info")
public class LogInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String logType;
    private String serverIp;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String resultCode;
    private JSONObject message;
    private String errorMessage;
}
