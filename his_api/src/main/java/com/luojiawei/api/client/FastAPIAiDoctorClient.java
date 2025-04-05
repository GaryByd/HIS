package com.luojiawei.api.client;

import com.luojiawei.common.domain.dto.ChatForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        value = "ai-doctor-service"
)
//TODO 等待......
public interface FastAPIAiDoctorClient {
    @PostMapping(
            value = "/api/eye-doctor/chat"
    )
    String eye_doctor_chat(
           @RequestBody ChatForm chatForm
    );

    
}

