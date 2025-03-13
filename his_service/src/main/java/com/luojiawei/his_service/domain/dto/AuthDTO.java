package com.luojiawei.his_service.domain.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthDTO {
    private String name;
    //映射成id_card json
    @JsonProperty("id_card")
    private String idCard;
    private String phone;
}
