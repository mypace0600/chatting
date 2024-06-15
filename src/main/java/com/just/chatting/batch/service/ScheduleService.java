package com.just.chatting.batch.service;

import com.just.chatting.batch.mapper.ScheduleMapper;
import com.just.chatting.batch.model.ScheduleLogModel;
import com.just.chatting.batch.model.ScheduleModel;
import com.just.chatting.common.Constant;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.quartz.impl.StdScheduler;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.TimeZone;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleMapper mapper;

    private final StdScheduler scheduler;

    /*
     *  스케줄 JobDetail 생성
     */
    public JobDetail getJobDetail(ScheduleModel model){
        JobBuilder jb = JobBuilder.newJob();
        jb.ofType(Constant.SCHEDULE_JOB.get(model.getScheduleNm()));
        jb.withIdentity(model.getScheduleNm(),model.getScheduleCl());
        return jb.build();
    }

    /*
     *  스케줄 Trigger 생성
     */
    public Trigger getTrigger(ScheduleModel model){
        TriggerBuilder<Trigger> tb = TriggerBuilder.newTrigger();
        tb.forJob(model.getScheduleNm(),model.getScheduleCl());
        tb.withIdentity(model.getScheduleNm(),model.getScheduleCl());
        tb.withSchedule(CronScheduleBuilder.cronSchedule(model.getExecuteCycle()).inTimeZone(TimeZone.getTimeZone("Asia/Seoul")));
        return tb.build();
    }

    /*
     *  스케줄 즉시 실행 - 나중에 화면 만들면 매개변수 타입을 ScheduleModel 로 변경
     */
    public String startJob(String scheduleNm){
        String result = "run success";
        ScheduleModel model = mapper.selectSchedule(scheduleNm);
        boolean simpleResult = false;
        try {
            if(model != null){
                scheduler.triggerJob(JobKey.jobKey(scheduleNm,model.getScheduleCl()));
            } else {
                log.info("스케줄 {}은 존재하지 않아 즉시 실행 불가",scheduleNm);
            }
        } catch (SchedulerException e){
            try {
                log.info("[ScheduleService][startJob]try to run job using simpleTrigger - "+scheduleNm);
                SimpleTrigger simpleTrigger = (SimpleTrigger) TriggerBuilder.newTrigger().build();
                scheduler.scheduleJob(getJobDetail(model),simpleTrigger);
                simpleResult = true;
            } catch (SchedulerException e1){
                result = "run failed : check log";
                log.warn("스케줄 {} 즉시 실행[simpleTrigger] 오류",scheduleNm);
                log.warn(e1.getMessage());
            }
            if(!simpleResult){
                result = "run failed : check log";
                log.warn("스케줄 {} 즉시 실행 오류",scheduleNm);
                log.warn(e.getMessage());
            }
        }
        return result;
    }

    /*
     *  스케줄 실행 상태
     *  서버 여러대일 경우 처리 확인 불가 -> T_MGR_SYS_SCHEDULE_LOCK 조회로 대체해야 함
     */
    public boolean isRunningJob(ScheduleModel model) {
        boolean result = false;
        List<JobExecutionContext> list = scheduler.getCurrentlyExecutingJobs();
        for(JobExecutionContext context : list){
            String scheduleName = context.getJobDetail().getKey().getName();
            String scheduleCl = context.getJobDetail().getKey().getGroup();
            if (StringUtils.equals(scheduleCl, model.getScheduleCl())
                    && StringUtils.equals(scheduleName, model.getScheduleNm())
                    && context.getFireTime().equals(context.getFireTime())) {
                result = true;
                break;
            }
        }
        return result;
    }

    /*
     *  스케줄 중지 : 스케줄 삭제
     */
    public void stopSchedule(ScheduleModel model){
        try {
            scheduler.deleteJob(JobKey.jobKey(model.getScheduleNm(),model.getScheduleCl()));
        } catch (SchedulerException e){
            log.warn("스케줄 {} 중지 오류",model.getScheduleNm());
            log.warn(e.getMessage());
        }
    }
    
    /*
     *  스케줄 시작 : 스케줄 추가
     */
    public void startSchedule(ScheduleModel model){
        try {
            scheduler.scheduleJob(getJobDetail(model),getTrigger(model));
        } catch (SchedulerException e){
            log.warn("스케줄 {} 시작 오류",model.getScheduleNm());
            log.warn(e.getMessage());
        }
    }

    /*
     *  스케줄 정보 조회
     */
    public ScheduleModel selectSchedule(String scheduleNm){
        return mapper.selectSchedule(scheduleNm);
    }

    /*
     *  스케줄 락 처리
     */
    @Transactional
    public int insertScheduleLock(ScheduleModel model){
        int result = 0;
        try {
            if(StringUtils.equals(Constant.NO, model.getLockYn())){
                result = 1;
            } else {
                if(mapper.selectScheduleLockCount(model)==0){
                    result = mapper.insertScheduleLock(model);
                }
            }
        } catch (DuplicateKeyException e){
            result = 0;
            log.info("스케줄 {} : 작업 중인 스케줄 있음",model.getScheduleNm());
        }
        return result;
    }

    /*
     *  스케줄 로그 기록 1
     */
    public int insertScheduleLog(ScheduleLogModel model){
        return mapper.insertScheduleLog(model);
    }

    /*
     *  스케줄 로그 기록 2
     */
    public void updateScheduleLog(ScheduleLogModel model){
        mapper.updateScheduleLog(model);
    }

    /*
     *  스케줄 락 해제
     */
    public void deleteScheduleLock(ScheduleModel model) {
        mapper.deleteScheduleLock(model);
    }

    /*
     *  스케줄 락 목록 조회
     */
    public List<ScheduleModel> selectScheduleLockList() {
        return mapper.selectScheduleLockList();
    }

    /*
     *  스케줄 실행 가능 목록 조회
     */
    public List<ScheduleModel> selectScheduleListAtStart() {
        return mapper.selectScheduleListAtStart();
    }

    /*
     *  스케줄 실행 결과 목록 조회
     */
    public List<ScheduleLogModel> selectScheduleLogAllList() {
        return mapper.selectScheduleLogAllList();
    }

    /*
     *  스케줄 실행 결과 목록 조회
     */
    public List<ScheduleLogModel> selectScheduleLogList() {
        return mapper.selectScheduleLogList();
    }

    /*
     *  스케줄 실행 결과 목록 조회 건수
     */
    public int selectScheduleLogListCount() {
        return mapper.selectScheduleLogListCount();
    }

    /*
     *  스케줄 실행 결과 조회
     */
    public ScheduleLogModel selectScheduleOne() {
        return mapper.selectScheduleOne();
    }

}
