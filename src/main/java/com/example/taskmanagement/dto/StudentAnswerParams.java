package com.example.taskmanagement.dto;

import lombok.Data;

@Data
public class StudentAnswerParams {
    private Long classId;
    private Long taskId;
    private Long studentId;
    private Long[] questionOptionIds;
    private ShortQuestionAnswer[] shortAnswers;
}