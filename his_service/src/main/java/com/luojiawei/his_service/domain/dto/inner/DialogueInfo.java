package com.luojiawei.his_service.domain.dto.inner;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DialogueInfo {
    @JsonProperty("role")
    private String role;
    @JsonProperty("content")
    private String content;
}
