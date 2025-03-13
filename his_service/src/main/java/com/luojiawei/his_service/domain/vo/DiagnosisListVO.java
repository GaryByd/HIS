package com.luojiawei.his_service.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class DiagnosisListVO {
    private Integer total;
    @JsonProperty("page")
    private Integer pageSize;
    @JsonProperty("limit")
    private Integer pageNumber;
    private List<Diagnosises> reports;
}
