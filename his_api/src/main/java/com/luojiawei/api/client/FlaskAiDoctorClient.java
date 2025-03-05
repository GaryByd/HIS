package com.luojiawei.api.client;

import com.luojiawei.api.config.FeignMultipartSupportConfig;
import com.luojiawei.api.fallback.FlaskUploadFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.transform.Result;

@FeignClient(
        name = "ai-service",
        configuration = FeignMultipartSupportConfig.class,
        fallbackFactory = FlaskUploadFallbackFactory.class
)
//TODO 等待......
public interface FlaskAiDoctorClient {
    @PostMapping(
            value = "/api/chat/completions"
    )
    String uploadFile(
            @RequestPart("file") MultipartFile file
    );
}

