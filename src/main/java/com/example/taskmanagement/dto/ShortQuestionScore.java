package com.example.taskmanagement.dto;

import lombok.Data;

@Data
public class ShortQuestionScore {
    private Long shortQuestionId;
    private Integer score;

    public ShortQuestionScore() {
    }

    public ShortQuestionScore(Long shortQuestionId, Integer score) {
        this.shortQuestionId = shortQuestionId;
        this.score = score;
    }
}
