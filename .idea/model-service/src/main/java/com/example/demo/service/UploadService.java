package com.example.demo.service;

import com.example.demo.domain.dto.ApiResponse;
import com.example.demo.domain.po.UploadResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface UploadService {

    /**
     * 处理文件上传逻辑
     */
    ApiResponse<UploadResponse>  handleFileUpload(MultipartFile file, String apiKey);
}