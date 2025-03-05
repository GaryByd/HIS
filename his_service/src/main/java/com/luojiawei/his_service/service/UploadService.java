package com.luojiawei.his_service.service;


import com.luojiawei.his_service.domain.dto.Result;
import com.luojiawei.his_service.domain.dto.UploadResponse;
import com.luojiawei.his_service.domain.po.AiModel;
import com.luojiawei.his_service.domain.vo.ApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface UploadService {

    /**
     * 处理文件上传逻辑
     */
    ApiResponse<UploadResponse> handleFileUpload(MultipartFile file, String apiKey);

    Result<AiModel> getModelInfo(Integer id);
}