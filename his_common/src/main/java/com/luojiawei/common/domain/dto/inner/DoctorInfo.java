package com.luojiawei.common.domain.dto.inner;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DoctorInfo {
    Integer id;
    private String name;
    private String department;
}



