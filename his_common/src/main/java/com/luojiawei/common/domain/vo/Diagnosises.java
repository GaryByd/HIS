package com.luojiawei.common.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Diagnosises {
        private Integer id;
        @JsonProperty("result")
        private String result;
        @JsonProperty("create_time")
        private String createTime;
        @JsonProperty("status")
        private String status;
        @JsonProperty("doctor_name")
        private String name;
        @JsonProperty("model_version")
        private String version;
    }