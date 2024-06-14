package com.just.chatting.batch.service;

import com.just.chatting.batch.model.ScheduleModel;
import com.just.chatting.common.Constant;
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
                if(StringUtils.equals(model.getServerIp(), util.getServerIP()) && StringUtils.equals(Constant.YES, model.getLockStopYn())){
                    if(scheduleService.isRunningJob(model)){
                        // 스케줄러 락 체크 시간 이상으로 실행 중인 스케줄러의 경우 처리
                    } else {
                        scheduleService.deleteScheduleLock(model);
                    }
                }
            }
        }
    }
}
