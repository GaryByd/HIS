package com.luojiawei.his_service.enums;


import lombok.Data;



public enum GeneralQuestions {
    EYE_CARE(1, "这种眼病有什么日常注意事项？", "日常护理"),
    SKIN_CARE(2, "如何保持皮肤健康？", "日常护理"),
    DIET_ADVICE(3, "有什么健康的饮食建议？", "健康饮食");

    private final int id;
    private final String question;
    private final String category;

    GeneralQuestions(int id, String question, String category) {
        this.id = id;
        this.question = question;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getCategory() {
        return category;
    }
}
