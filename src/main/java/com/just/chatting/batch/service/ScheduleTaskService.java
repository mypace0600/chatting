package com.just.chatting.batch.service;

import com.just.chatting.batch.model.ScheduleModel;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ScheduleTaskService {
    @Resource
    private ScheduleService scheduleService;

    public void checkLock(){
        List<ScheduleModel> list = scheduleService.selectScheduleLockList();
        if(list != null){
            for(ScheduleModel model : list){
                if(StringUtils.equals(model.getServerIp()))
            }
        }
    }
}
