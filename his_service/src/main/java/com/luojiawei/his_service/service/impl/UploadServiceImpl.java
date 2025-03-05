package com.luojiawei.his_service.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luojiawei.api.client.FlaskUploadClient;
import com.luojiawei.his_service.domain.dto.Result;
import com.luojiawei.his_service.domain.dto.UploadResponse;
import com.luojiawei.his_service.domain.po.AiModel;
import com.luojiawei.his_service.domain.vo.ApiResponse;

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
    private final FlaskUploadClient flaskUploadClient;
    /**
     * 处理文件上传逻辑
     */
    @Override
    public ApiResponse<UploadResponse> handleFileUpload(MultipartFile file, String apiKey) {
        //负载均衡
        try {
            // 调用 Flask 服务上传文件
            ResponseEntity<String> responseEntity = flaskUploadClient.uploadFile(file, apiKey);

            // 检查 HTTP 响应是否成功
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                String responseBody = responseEntity.getBody();

                // 处理响应体为空的情况
                if (responseBody == null || responseBody.isEmpty()) {
                    return ApiResponse.error("上传成功，但响应体为空");
                }

                // 解析响应体
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(responseBody);  // 将字符串解析为JsonNode

                // 从根节点获取result节点
                JsonNode resultNode = rootNode.path("result");


                // 获取disease_labels和pred_probs
                List<String> diseaseLabels = objectMapper.readValue(resultNode.path("disease_labels").toString(), List.class);
                List<Double> predProbs = objectMapper.readValue(resultNode.path("pred_probs").toString(), List.class);

                // 构建UploadResponse对象
                UploadResponse uploadResponse = new UploadResponse();
                UploadResponse.Result result = new UploadResponse.Result();
                Map<String, Double> predClassProbabilities = new HashMap<>();

                // 填充pred_class_probabilities
                for (int i = 0; i < diseaseLabels.size(); i++) {
                    predClassProbabilities.put(diseaseLabels.get(i), predProbs.get(i));
                }
                result.setPredClassProbabilities(predClassProbabilities);

                // 设置最大概率标签
                double maxProb = predProbs.get(1);  // 假设最大概率为第二个标签
                String maxProbLabel = diseaseLabels.get(1);  // 对应的标签
                UploadResponse.MaxProbLabel maxLabel = new UploadResponse.MaxProbLabel();
                maxLabel.setLabel(maxProbLabel);
                maxLabel.setProbability(maxProb);
                result.setMaxProbLabel(maxLabel);

                // 设置结果和状态
                uploadResponse.setResult(result);
                uploadResponse.setStatus("success");

                // 返回 ApiResponse
                return ApiResponse.success(uploadResponse);
            } else {
                return ApiResponse.error("上传失败");
            }
        } catch (Exception e) {
            throw new RuntimeException("上传过程中发生异常", e);
        }
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