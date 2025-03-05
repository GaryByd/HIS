package com.luojiawei.model.service;

import com.luojiawei.model.domain.dto.ApiResponse;
import com.luojiawei.model.domain.po.UploadResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface UploadService {

    /**
     * 处理文件上传逻辑
     */
    ApiResponse<UploadResponse>  handleFileUpload(MultipartFile file, String apiKey);
}