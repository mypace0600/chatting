package com.just.chatting.batch.mapper;

import com.just.chatting.batch.model.ScheduleLogModel;
import com.just.chatting.batch.model.ScheduleModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ScheduleMapper {

    ScheduleModel selectSchedule(String scheduleNm);

    int selectScheduleLockCount(ScheduleModel model);

    int insertScheduleLock(ScheduleModel model);

    int insertScheduleLog(ScheduleLogModel model);

    void updateScheduleLog(ScheduleLogModel model);

    void deleteScheduleLock(ScheduleModel model);

    List<ScheduleModel> selectScheduleLockList();

    List<ScheduleModel> selectScheduleListAtStart();

    List<ScheduleLogModel> selectScheduleLogAllList();

    List<ScheduleLogModel> selectScheduleLogList();

    int selectScheduleLogListCount();

    ScheduleLogModel selectScheduleOne();
}
