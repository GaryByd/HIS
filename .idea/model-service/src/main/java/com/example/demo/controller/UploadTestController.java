package com.example.demo.controller;

import com.example.demo.domain.dto.ApiResponse;
import com.example.demo.domain.po.UploadResponse;
import com.example.demo.service.UploadService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8080") // 允许前端跨域访问
public class UploadTestController {
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
    @PostMapping
    public ApiResponse<UploadResponse> testFileUpload(
            @RequestParam("file") MultipartFile file,
            @RequestHeader(value = "X-API-Key", required = false) String apiKey) {
        System.out.printf("收到文件上传请求，文件名:" + file.getName());
        return uploadService.handleFileUpload(file, apiKey);
    }
}