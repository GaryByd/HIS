package com.luojiawei.his_service.domain.vo;

import lombok.Data;

@Data
public class Diagnosises {
        private Integer id;
        private String disease_name;
        private String disease_category;
        private String create_time;
        private String status;
        private String doctor_name;
        private String model_version;
    }