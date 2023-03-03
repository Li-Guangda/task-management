package com.example.taskmanagement.dto;

import lombok.Data;

@Data
public class ShortAnswerQuestionScore {
    private Long shortAnswerQuestionId;
    private Long studentId;
    private Integer score;
}
