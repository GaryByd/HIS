package com.luojiawei.his_service.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luojiawei.api.client.FlaskModelAnalyzeClient;


import com.luojiawei.common.domain.dto.Result;
import com.luojiawei.common.domain.dto.inner.UploadResponse;

import com.luojiawei.common.domain.po.AiModel;
import com.luojiawei.common.domain.vo.ApiResponse;
import com.luojiawei.his_service.mapper.AiModelMapper;
import com.luojiawei.his_service.service.UploadService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class UploadServiceImpl implements UploadService {

    private  final AiModelMapper aiModelMapper;
    private final FlaskModelAnalyzeClient flaskUploadClient;
    /**
     * 处理文件上传逻辑
     */
    @Override
    public String handleFileUpload(MultipartFile file, String apiKey) {
        return flaskUploadClient.uploadFile(file, apiKey);
    }

    @Override
    public Result<AiModel> getModelInfo(Integer id) {
        AiModel aiModel = aiModelMapper.selectById(id);
        if(aiModel == null){
            return Result.fail("模型不存在");
        }
        return Result.ok("Success", aiModel);
    }

    /**
     * 错误信息解析
     */
    private String parseError(Exception e) {
        if (e instanceof FeignException) {
            FeignException fe = (FeignException) e;
            String responseBody = fe.contentUTF8();
            // 尝试解析JSON格式的错误响应
            try {
                JsonNode node = new ObjectMapper().readTree(responseBody);
                if (node.has("error_code")) {
                    int code = node.get("error_code").asInt();
                    String msg = node.get("message").asText();
                    return String.format("服务端错误 [%d]: %s", code, msg);
                }
            } catch (JsonProcessingException ex) {
                log.error("解析错误响应失败", ex);
            }

            return "服务通信错误: " + responseBody;
        }
        return "系统内部错误: " + e.getMessage();
    }
}