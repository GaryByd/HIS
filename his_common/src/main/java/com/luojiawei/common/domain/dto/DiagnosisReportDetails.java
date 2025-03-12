package com.luojiawei.common.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.luojiawei.common.domain.dto.inner.DoctorInfo;
import com.luojiawei.common.domain.dto.inner.PatientInfo;
import com.luojiawei.common.domain.po.AiModel;
import com.luojiawei.common.domain.po.EyeImages;
import com.luojiawei.common.domain.po.MedicationRecommendations;
import com.luojiawei.common.domain.po.TreatmentPlans;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;


@Data
@AllArgsConstructor
public class DiagnosisReportDetails {
    private Integer id;
    @JsonProperty("patient_info")
    private PatientInfo patientInfo;
    @JsonProperty("doctor_info")
    private DoctorInfo doctorInfo;
    @JsonProperty("disease_name")
    private String diseaseName;
    @JsonProperty("disease_category")
    private String diseaseCategory;
    private String result;
    @JsonProperty("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    private String status;
    private Integer score;
    private String remark;
    @JsonProperty("eye_images")
    private ArrayList<EyeImages> eyeImages;
    @JsonProperty("treatment_plan")
    private TreatmentPlans treatmentPlan;
    @JsonProperty("medications")
    private ArrayList<MedicationRecommendations> medications;
    @JsonProperty("model_info")
    private AiModel aiModel;

}

