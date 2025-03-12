package com.luojiawei.common.domain.vo.inner;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionsVo {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("question")
    private String question;
    @JsonProperty("category")
    private String category;
}
