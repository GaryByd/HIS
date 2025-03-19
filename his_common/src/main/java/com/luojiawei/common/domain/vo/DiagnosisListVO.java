package com.luojiawei.common.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

import java.util.List;

@Data
public class DiagnosisListVO {
    private Integer total;
    @JsonProperty("limit")
    private Integer pageSize;
    @JsonProperty("page")
    private Integer pageNumber;
    private List<Diagnosises> reports;
}
