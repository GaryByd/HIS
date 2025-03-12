package com.luojiawei.api.client;

import com.luojiawei.api.config.FeignMultipartSupportConfig;
import com.luojiawei.api.fallback.FlaskUploadFallbackFactory;
import com.luojiawei.common.domain.dto.ChatForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.transform.Result;

@FeignClient(
        value = "ai-doctor-service"
)
//TODO 等待......
public interface FlaskAiDoctorClient {
    @PostMapping(
            value = "/api/eye-doctor/chat"
    )
    String eye_doctor_chat(
           @RequestBody ChatForm chatForm
    );
}

