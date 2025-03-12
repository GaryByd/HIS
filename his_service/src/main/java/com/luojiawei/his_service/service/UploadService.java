package com.luojiawei.his_service.service;



import com.luojiawei.common.domain.dto.Result;
import com.luojiawei.common.domain.dto.inner.UploadResponse;
import com.luojiawei.common.domain.po.AiModel;
import com.luojiawei.common.domain.vo.ApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface UploadService {

    /**
     * 处理文件上传逻辑
     */
    String handleFileUpload(MultipartFile file, String apiKey);

    Result<AiModel> getModelInfo(Integer id);
}