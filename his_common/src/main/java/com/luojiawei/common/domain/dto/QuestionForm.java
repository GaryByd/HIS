package com.luojiawei.common.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.luojiawei.common.domain.dto.inner.DialogueInfo;
import lombok.Data;

import java.util.ArrayList;

@Data
public class QuestionForm {
    @JsonProperty("previous_conversations")
    private ArrayList<DialogueInfo> dialogueInfoList;
    private String question;
}
