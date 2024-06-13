package com.just.chatting.batch.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class JobPointResetService {

    @Transactional
    public void pointReset(JSONObject msg){
        JSONObject result = new JSONObject();
    }
}
