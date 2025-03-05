package com.luojiawei.model.mapper;

import com.luojiawei.model.domain.dto.ApiResponse;
import com.luojiawei.model.domain.po.UploadResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import com.luojiawei.api.client.FlaskUploadClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UploadMapper {

    @Autowired
    private final FlaskUploadClient flaskUploadClient;

    public ApiResponse<UploadResponse> uploadFile(MultipartFile file, String apiKey) {
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
}
