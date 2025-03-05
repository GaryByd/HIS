package com.luojiawei.api.dto;

import com.luojiawei.api.dto.inner.Message;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class AiDoctorResponse {
    private Message message;

}
