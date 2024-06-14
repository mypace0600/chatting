package com.just.chatting.batch.model;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

import java.time.LocalDateTime;

@Getter
@Setter
public class ScheduleLogModel {
    private String resultCode;
    private String errorMessage;
    private JSONObject message;
    private LocalDateTime startDt;
    private LocalDateTime endDt;
    private String ifTy;
    private String serverIp;
}
