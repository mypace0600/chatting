package com.just.chatting.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="t_schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String scheduleNm;
    private String scheduleCl;
    private String executeCycle;
    private String timeZone;
    private String scheduleDsc;
    private String objectNm;
    private String triggerNm;
    private String useYn;
    private String serverIp;
    private String lockYn;
    private String lockCycle;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lockRgsDt;

    private String lockStopYn;

    @ManyToOne
    @JoinColumn(name = "registeredId", referencedColumnName = "id", nullable = false)
    private User registeredUser;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registeredDate;

    @ManyToOne
    @JoinColumn(name = "updatedId", referencedColumnName = "id", nullable = false)
    private User updatedUser;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedDate;
}
