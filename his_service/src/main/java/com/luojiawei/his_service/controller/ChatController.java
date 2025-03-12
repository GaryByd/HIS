package com.luojiawei.his_service.controller;


import com.luojiawei.api.client.FlaskAiDoctorClient;

import com.luojiawei.common.domain.dto.ChatForm;
import com.luojiawei.common.domain.dto.DiagnosisReportDetails;
import com.luojiawei.common.domain.dto.QuestionForm;


import com.luojiawei.common.domain.dto.Result;
import com.luojiawei.his_service.service.IDiagnosisReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ChatController {

    private final IDiagnosisReportService diagnosisReportService;
    private final FlaskAiDoctorClient flaskAiDoctorClient;
    @PostMapping("/diagnosis/{diagnosisId}/qa/ask")
    public String ask(@PathVariable("diagnosisId") Integer diagnosisId, @RequestBody QuestionForm questionForm) {
        Result<DiagnosisReportDetails> objectResult = diagnosisReportService.diagnosisReport(diagnosisId);
        DiagnosisReportDetails diagnosisReportDetails = objectResult.getData();
        //构造聊天的参数
        ChatForm chatForm= new ChatForm();
        chatForm.setDiagnosisName(diagnosisReportDetails.getDiseaseName());
        chatForm.setDiagnosisCategory(diagnosisReportDetails.getDiseaseCategory());
        chatForm.setResult(diagnosisReportDetails.getResult());
        chatForm.setRemark(diagnosisReportDetails.getRemark());
        chatForm.setPreviousConversations(questionForm.getDialogueInfoList());
        chatForm.setQuestion(questionForm.getQuestion());
        chatForm.setTreatmentPlan(diagnosisReportDetails.getTreatmentPlan());
        chatForm.setMedications(diagnosisReportDetails.getMedications());
        chatForm.setStream(false);

        return flaskAiDoctorClient.eye_doctor_chat(chatForm);
    }
}
