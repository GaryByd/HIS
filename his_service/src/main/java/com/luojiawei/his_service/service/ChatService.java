package com.luojiawei.his_service.service;

import com.luojiawei.his_service.domain.dto.ChatForm;
import com.luojiawei.his_service.domain.dto.Result;

public interface ChatService {
    Result<Object> ask(Integer diagnosisId, ChatForm chatForm);
}
