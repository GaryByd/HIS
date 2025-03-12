package com.luojiawei.common.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.luojiawei.common.domain.vo.inner.QuestionsVo;
import lombok.Data;

import java.util.ArrayList;

@Data
public class RecommendedQuestions {
    @JsonProperty("questions")
    ArrayList<QuestionsVo> questions;
}
