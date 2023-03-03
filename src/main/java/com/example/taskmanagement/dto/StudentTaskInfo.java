package com.example.taskmanagement.dto;

import lombok.Data;

import java.util.List;

@Data
public class StudentTaskInfo {
    private List<Long> choiceQuestionOptionIds;
    private List<ShortQuestionAnswer> shortQuestionAnswers;
}
