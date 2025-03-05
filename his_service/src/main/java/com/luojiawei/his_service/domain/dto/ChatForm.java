package com.luojiawei.his_service.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.luojiawei.his_service.domain.dto.inner.DialogueInfo;

import java.util.ArrayList;

public class ChatForm {
    @JsonProperty("previous_conversations")
    private ArrayList<DialogueInfo> dialogueInfoList;
    @JsonProperty("question")
    private String question;
}
