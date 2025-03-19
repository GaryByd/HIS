package com.luojiawei.his_service.controller;


import com.luojiawei.api.client.FlaskAiDoctorClient;

import com.luojiawei.common.domain.dto.ChatForm;
import com.luojiawei.common.domain.dto.DiagnosisReportDetails;
import com.luojiawei.common.domain.dto.QuestionForm;


import com.luojiawei.common.domain.dto.Result;
import com.luojiawei.his_service.service.IDiagnosisReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ChatController {

    private final IDiagnosisReportService diagnosisReportService;
    private final FlaskAiDoctorClient flaskAiDoctorClient;
    private final ExecutorService executorService = Executors.newCachedThreadPool(); // 线程池管理
    @PostMapping("/diagnosis/{diagnosisId}/qa/ask")
    public Object ask(@PathVariable("diagnosisId") Integer diagnosisId, @RequestBody QuestionForm questionForm) {
        Result<DiagnosisReportDetails> objectResult = diagnosisReportService.diagnosisReport(diagnosisId);
        DiagnosisReportDetails diagnosisReportDetails = objectResult.getData();
        ChatForm chatForm = new ChatForm();
        chatForm.setDiagnosisName(diagnosisReportDetails.getDiseaseName());
        chatForm.setDiagnosisCategory(diagnosisReportDetails.getDiseaseCategory());
        chatForm.setResult(diagnosisReportDetails.getResult());
        chatForm.setRemark(diagnosisReportDetails.getRemark());
        chatForm.setPreviousConversations(questionForm.getDialogueInfoList());
        chatForm.setQuestion(questionForm.getQuestion());
        chatForm.setTreatmentPlan(diagnosisReportDetails.getTreatmentPlan());
        chatForm.setMedications(diagnosisReportDetails.getMedications());
        chatForm.setStream(questionForm.getStream());
        //构造聊天的参数
        if (!questionForm.getStream()) {
            return flaskAiDoctorClient.eye_doctor_chat(chatForm);
        }
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();
        Future<?> future = executorService.submit(() -> {
            int emptyCount = 0; // 记录连续获取不到数据的次数
            final int MAX_EMPTY_COUNT = 5; // 连续 5 次无数据后停止
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    String logEntry = flaskAiDoctorClient.eye_doctor_chat(chatForm);
                    if (logEntry != null) {
                        if ("data: [DONE]".equals(logEntry.trim())) {
                            break;
                        }
                        emitter.send(logEntry);
                        emptyCount = 0; // 重置计数器
                    }
                    else {
                        emptyCount++; // 计数 +1
                        if (emptyCount >= MAX_EMPTY_COUNT) {
                            break; // 超过最大限制，停止线程
                        }
                        Thread.sleep(500);
                    }

                }
            } catch (Exception e) {
                emitter.completeWithError(e); // 发送错误信息
            } finally {
                emitter.complete(); // 关闭流
            }
        });

        // 监听客户端断开连接，自动终止线程
        emitter.onCompletion(() -> future.cancel(true));
        emitter.onTimeout(() -> future.cancel(true));
        return emitter;
    }
}
