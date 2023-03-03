package com.example.taskmanagement.dto;

import lombok.Data;

@Data
public class RemarkParams {
    private Long classId;
    private Long taskId;
    private Long studentId;
    private String remark;
    private ShortAnswerQuestionScore[] shortAnswerQuestionScores;
}

