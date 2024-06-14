package com.just.chatting.batch.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
public class ScheduleModel {
    private String scheduleName;
    private String scheduleCl;
    private String executeCycle;
    private String timeZone;
    private String scheduleDsc;
    private String objectNm;
    private String triggerNm;
    private String lockYn;
    private String lockCycle;
    private String useYn;
    private String rgstId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime rgstDt;
    private String modiId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modiDt;
    private String serverIp;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lockRgsDt;
    private String lockStopYn;
}
