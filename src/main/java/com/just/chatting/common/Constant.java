package com.just.chatting.common;

import com.just.chatting.batch.job.JobPointReset;
import org.quartz.Job;

import java.util.HashMap;
import java.util.Map;

// 공통 상수 처리
public interface Constant {
    public static final Map<String, Class<? extends Job>> SCHEDULE_JOB = new HashMap<String, Class<? extends Job>>(){
        private static final long serialVersionUID = 645511981042993719L;
        {
            put("POINT_RESET", JobPointReset.class);
        }
    };

    public interface POINT_SYS {
        public static final String POINT_RESET = "point_reset";
    }

    public static final String YES = "Y";
    public static final String NO = "N";
    public static final String UNKNOWN = "unknown";
    public static final String RESULT = "result";
    public static final String ERROR_MESSAGE = "error_message";

}
