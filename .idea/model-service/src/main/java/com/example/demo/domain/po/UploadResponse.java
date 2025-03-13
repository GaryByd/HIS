package com.example.demo.domain.po;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class UploadResponse {
    private String status;

    private Result result;

    @Data
    public static class Result {
        @JsonProperty("max_prob_label")
        private MaxProbLabel maxProbLabel;

        @JsonProperty("pred_class_probabilities")
        private Map<String, Double> predClassProbabilities;
    }

    @Data
    public static class MaxProbLabel {
        private String label;
        private Double probability;
    }
}