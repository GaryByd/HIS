package com.luojiawei.his_service.domain.dto.inner;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PatientInfo {
    private String name;
    @JsonProperty("id_card")
    private String idCard;
}