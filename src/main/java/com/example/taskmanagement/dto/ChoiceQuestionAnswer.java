package com.example.taskmanagement.dto;

import lombok.Data;

@Data
public class ChoiceQuestionAnswer {
    private Long choiceQuestionId;
    private Integer sequenceNumber;
    private Integer type;
    private String rightAnswer;
    private String myAnswer;
    private Integer score;
    private String  classroomAccuracy;

    public ChoiceQuestionAnswer() {}

    public ChoiceQuestionAnswer(Long choiceQuestionId, Integer sequenceNumber, Integer type, String rightAnswer,
                                String myAnswer, Integer score, String classroomAccuracy) {
        this.choiceQuestionId = choiceQuestionId;
        this.sequenceNumber = sequenceNumber;
        this.type = type;
        this.rightAnswer = rightAnswer;
        this.myAnswer = myAnswer;
        this.score = score;
        this.classroomAccuracy = classroomAccuracy;
    }
}
