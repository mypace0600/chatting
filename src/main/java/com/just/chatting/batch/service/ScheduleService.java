package com.just.chatting.batch.service;

import com.just.chatting.batch.model.ScheduleModel;
import com.just.chatting.common.Constant;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.impl.StdScheduler;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ScheduleService {
    @Resource
    private ScheduleMapper mapper;

    @Resource
    private StdScheduler scheduler;

    @Resource
    private CodeMapper codeMapper;

    /*
    *  스케줄 JobDetail 생성
    */
    public JobDetail getJobDetail(ScheduleModel model){
        JobBuilder jb = JobBuilder.newJob();
        jb.ofType(Constant.SCHEDULE_JOB.get(model.getScheduleName()));
        jb.withIdentity(model.getScheduleName(),model.getScheduleCl());
        return jb.build();
    }
}
