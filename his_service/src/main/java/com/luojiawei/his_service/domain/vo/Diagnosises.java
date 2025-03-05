package com.luojiawei.his_service.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Diagnosises {
        private Integer id;
        @JsonProperty("disease_name")
        private String diseaseName;
        @JsonProperty("disease_category")
        private String diseaseCategory;
        @JsonProperty("disease_description")
        private String createTime;
        @JsonProperty("status")
        private String status;
        @JsonProperty("doctor_name")
        private String name;
        @JsonProperty("model_version")
        private String version;
    }