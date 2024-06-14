package com.just.chatting.batch.job;

import com.just.chatting.batch.model.ScheduleModel;
import com.just.chatting.common.Constant;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.impl.StdScheduler;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /*
     *  스케줄 즉시 실행 - 나중에 화면 만들면 매개변수 타입을 ScheduleModel 로 변경
     */
    public String startJob(String scheduleNm){

    }


    public List<ScheduleModel> selectScheduleLockList() {
    }

    public boolean isRunningJob(ScheduleModel model) {
        boolean result = false;
        List<JobExecutionContext> list = scheduler.getCurrentlyExecutingJobs();
        for(JobExecutionContext context : list){
            String scheduleName = context.getJobDetail().getKey().getName();
            String scheduleCl = context.getJobDetail().getKey().getGroup();
            if (StringUtils.equals(scheduleCl, model.getScheduleCl())
                    && StringUtils.equals(scheduleName, model.getScheduleName())
                    && context.getFireTime().equals(context.getFireTime())) {
                result = true;
                break;
            }
        }
        return result;
    }

    public void deleteScheduleLock(ScheduleModel model) {
    }
}
