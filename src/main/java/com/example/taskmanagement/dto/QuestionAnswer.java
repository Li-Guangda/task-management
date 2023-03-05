package com.example.taskmanagement.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuestionAnswer {
    private Long taskId;
    private Long studentId;
    private Integer totalScore;
    private List<ChoiceQuestionAnswer> choiceQuestionAnswer;
    private List<ShortQuestionScore> shortQuestionScores;
    public QuestionAnswer() {
    }

    public QuestionAnswer(Long taskId, Long studentId, Integer totalScore,
                          List<ChoiceQuestionAnswer> choiceQuestionAnswer, List<ShortQuestionScore> shortQuestionScores) {
        this.taskId = taskId;
        this.studentId = studentId;
        this.totalScore = totalScore;
        this.choiceQuestionAnswer = choiceQuestionAnswer;
        this.shortQuestionScores = shortQuestionScores;
    }
}
