package com.luojiawei.common.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.luojiawei.common.domain.dto.inner.DialogueInfo;
import com.luojiawei.common.domain.po.MedicationRecommendations;
import com.luojiawei.common.domain.po.TreatmentPlans;
import lombok.Data;

import java.util.ArrayList;

@Data
public class ChatForm {
    @JsonProperty("disease_name")
    private String diagnosisName;
    @JsonProperty("disease_category")
    private String diagnosisCategory;
    @JsonProperty("result")
    private String result;
    @JsonProperty("remark")
    private String remark;
    @JsonProperty("treatment_plan")
    private TreatmentPlans treatmentPlan;
    @JsonProperty("medications")
    private ArrayList<MedicationRecommendations> medications;
    @JsonProperty("previous_conversations")
    private ArrayList<DialogueInfo> previousConversations;
    @JsonProperty("question")
    private String question;
    @JsonProperty("stream")
    private boolean stream;


}
