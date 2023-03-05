package com.example.taskmanagement.dto;

import lombok.Data;

import java.util.List;

@Data
public class TaskContent {
    private Long taskId;
    private List<ChoiceQuestion> choiceQuestions;
    private List<ShortQuestion> shortQuestions;

    public TaskContent(Long taskId, List<ChoiceQuestion> choiceQuestions, List<ShortQuestion> shortQuestions) {
        this.taskId = taskId;
        this.choiceQuestions = choiceQuestions;
        this.shortQuestions = shortQuestions;
    }
}
