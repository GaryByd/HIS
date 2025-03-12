package com.luojiawei.api.client;

import com.luojiawei.api.config.FeignMultipartSupportConfig;
import com.luojiawei.api.fallback.FlaskUploadFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;


@FeignClient(
        name = "flask-service",
        configuration = FeignMultipartSupportConfig.class,
        fallbackFactory = FlaskUploadFallbackFactory.class
)
public interface FlaskModelAnalyzeClient {

    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    String uploadFile(
            @RequestPart("file") MultipartFile file,
            @RequestHeader("X-API-Key") String apiKey
    );
}
