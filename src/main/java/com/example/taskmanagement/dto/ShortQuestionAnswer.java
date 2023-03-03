package com.example.taskmanagement.dto;

import lombok.Data;

@Data
public class ShortQuestionAnswer {
    private Long shortAnswerQuestionId;
    private String answer;
    private Integer score;

    public ShortQuestionAnswer() {}

    public ShortQuestionAnswer(Long shortAnswerQuestionId, String answer, Integer score) {
        this.shortAnswerQuestionId = shortAnswerQuestionId;
        this.answer = answer;
        this.score = score;
    }
}
