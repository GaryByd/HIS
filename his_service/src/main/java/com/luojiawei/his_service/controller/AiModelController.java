package com.luojiawei.his_service.controller;


import com.luojiawei.his_service.domain.dto.Result;
import com.luojiawei.his_service.domain.dto.UploadResponse;
import com.luojiawei.his_service.domain.po.AiModel;
import com.luojiawei.his_service.domain.vo.ApiResponse;
import com.luojiawei.his_service.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/model")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8080") // 允许前端跨域访问
public class AiModelController {
    private final UploadService uploadService;
    // 配置建议添加到application.yml
    /*
    spring:
      servlet:
        multipart:
          max-file-size: 100MB
          max-request-size: 100MB
    */
    /**
     * 测试端点1：真实文件上传
     * 使用方式：通过Postman或表单上传文件
     */
    @PostMapping("info")
    public ApiResponse<UploadResponse> testFileUpload(
            @RequestParam("file") MultipartFile file,
            @RequestHeader(value = "X-API-Key", required = false) String apiKey) {
        System.out.printf("收到文件上传请求，文件名:" + file.getName());
        return uploadService.handleFileUpload(file, apiKey);
    }


    @ApiOperation("查看模型参数")
    @GetMapping("/{id}/info")
    public Result<AiModel> getModelInfo(
            @PathVariable("id") Integer id
    ) {
        return uploadService.getModelInfo(id);
    }
}