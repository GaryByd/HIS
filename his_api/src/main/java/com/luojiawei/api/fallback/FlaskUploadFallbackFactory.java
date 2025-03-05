package com.luojiawei.api.fallback;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import com.luojiawei.api.client.FlaskUploadClient;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

// Fallback实现
@Component
@Slf4j
public class FlaskUploadFallbackFactory implements FallbackFactory<FlaskUploadClient> {

    @Override
    public FlaskUploadClient create(Throwable cause) {
        return new FlaskUploadClient() {
            @Override
            public ResponseEntity<String> uploadFile(MultipartFile file, String apiKey) {
                log.error("调用Flask上传服务失败", cause);
                
                // 解析Feign异常
                if (cause instanceof FeignException) {
                    FeignException ex = (FeignException) cause;
                    return ResponseEntity.status(ex.status())
                        .body("错误代码: " + parseErrorCode(ex.contentUTF8()));
                }

                //TODO 重新改一下这些响应信息
//                return ResponseEntity.unprocessableEntity();
//                    .body("服务不可用");
                return ResponseEntity
                        .of("服务不可用" .describeConstable());
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