package com.just.chatting.controller;

import com.just.chatting.batch.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @RequestMapping(value = "/schedule/{scheduleName}", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> testSchedule(@PathVariable String scheduleName){
        Map<String, Object> result = new HashMap<String,Object>();
        result.put("result",scheduleService.startJob(scheduleName));
        return result;
    }
}
