package com.luojiawei.api.fallback;

import com.luojiawei.api.client.FlaskModelAnalyzeClient;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

// Fallback实现
@Component
@Slf4j
public class FlaskUploadFallbackFactory implements FallbackFactory<FlaskModelAnalyzeClient> {

    @Override
    public FlaskModelAnalyzeClient create(Throwable cause) {
        return new FlaskModelAnalyzeClient() {
            @Override
            public String uploadFile(MultipartFile file, String apiKey) {
                log.error("调用Flask上传服务失败", cause);
                if (cause instanceof FeignException) {
                    FeignException feignException = (FeignException) cause;
                    String response = feignException.contentUTF8();
                    String errorCode = parseErrorCode(response);
                    return "调用Flask上传服务失败，错误码：" + errorCode;
                }
                return "调用Flask上传服务失败，错误信息：" + cause.getMessage();

            }
            
            private String parseErrorCode(String response) {
                // 实现具体的错误码解析逻辑
                if (response.contains("1003")) {
                    return "文件验证失败";
                }
                return "未知错误";
            }
        };
    }
}