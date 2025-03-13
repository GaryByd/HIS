package com.luojiawei.his_service.domain.dto.inner;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DoctorInfo {
    Integer id;
    private String name;
    private String department;
}



