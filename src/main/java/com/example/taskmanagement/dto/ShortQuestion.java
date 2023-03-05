package com.example.taskmanagement.dto;

import lombok.Data;

@Data
public class ShortQuestion {
    private Long shortQuestionId;
    private Integer sequenceNumber;
    private String title;
    private Integer score;

    public ShortQuestion() {}

    public ShortQuestion(Long shortQuestionId, Integer sequenceNumber, String title, Integer score) {
        this.shortQuestionId = shortQuestionId;
        this.sequenceNumber = sequenceNumber;
        this.title = title;
        this.score = score;
    }
}