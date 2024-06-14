package com.just.chatting.batch.job;

import com.just.chatting.batch.model.ScheduleLogModel;
import com.just.chatting.batch.model.ScheduleModel;
import com.just.chatting.batch.service.JobPointResetService;
import com.just.chatting.batch.service.ScheduleService;
import com.just.chatting.batch.service.ScheduleTaskService;
import com.just.chatting.common.CommonUtil;
import com.just.chatting.common.Constant;
import io.netty.util.internal.StringUtil;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobPointReset extends QuartzJobBean {


    private final JobPointResetService service;
    private final ScheduleService scheduleService;
    private final CommonUtil util;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        String resultCode = "No";
        String errorMessage = "";

        ScheduleModel model = scheduleService.selectSchedule("POINT_RESET");
        model.setServerIp(util.getServerIP());
        int lock = 0;

        ScheduleLogModel logModel = new ScheduleLogModel();

        logModel.setStartDt(LocalDateTime.now());
        logModel.setIfTy(model.getScheduleName());
        logModel.setServerIp(model.getServerIp());

        int insLogRslt = scheduleService.insertScheduleLog(logModel);
        JSONObject msg = new JSONObject();
        lock = scheduleService.insertScheduleLock(model);

        if(lock==1){
            log.info("POINT_RESET 배치 시작");
            try {
                service.pointReset(msg);
                if(StringUtils.equals(msg.getJSONObject(Constant.POINT_SYS.POINT_RESET).getString(Constant.RESULT),Constant.YES)){
                    resultCode = Constant.YES;
                } else {
                    resultCode = Constant.NO;
                    errorMessage = msg.getJSONObject(Constant.POINT_SYS.POINT_RESET).getString(Constant.ERROR_MESSAGE);
                }
            } catch (Exception e){
                log.warn("배치 오류");
                log.warn(e.getMessage());
                resultCode = Constant.NO;
                errorMessage = util.getExceptionTrace(e);
            }

            logModel.setResultCode(resultCode);
            logModel.setMessage(msg);
            logModel.setErrorMessage(errorMessage);
            logModel.setEndDt(LocalDateTime.now());

            if(insLogRslt != 1){
                scheduleService.insertScheduleLog(logModel);
            } else {
                scheduleService.updateScheduleLog(logModel);
            }
            log.info("배치 종료");
        }
    }
}
