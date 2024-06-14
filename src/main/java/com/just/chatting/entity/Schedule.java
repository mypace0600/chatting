package com.just.chatting.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="t_schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String scheduleName;
    private String scheduleCl;
    private String executeCycle;
    private String timeZone;
    private String scheduleDetail;
    private String objectName;
    private String triggerName;
    private String lockYn;
    private String lockCycle;
    private String useYn;
    private LocalDateTime registeredDate;
    private LocalDateTime updatedDate;
}
