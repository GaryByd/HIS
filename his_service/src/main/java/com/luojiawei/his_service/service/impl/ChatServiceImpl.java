package com.luojiawei.his_service.service.impl;

import com.luojiawei.api.client.FlaskAiDoctorClient;
import com.luojiawei.his_service.domain.dto.ChatForm;
import com.luojiawei.his_service.domain.dto.Result;
import com.luojiawei.his_service.service.ChatService;

public class ChatServiceImpl implements ChatService {
    FlaskAiDoctorClient flaskAiDoctorClient;

    @Override
    public Result<Object> ask(Integer diagnosisId, ChatForm chatForm) {
        return null;
    }
}
