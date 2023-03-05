package com.example.taskmanagement.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Data
public class ChoiceQuestion {
    private Long choiceQuestionId;
    @Min(value = 1, message = "The sequence number must be greater than 0")
    private Integer sequenceNumber;
    @NotBlank(message = "The question title can not be blank")
    private String title;
    @Range(min = 0, max = 1, message = "'type' field can only be 0 or 1")
    private Integer type;
    @Range(min = 1, max = 10, message = "'score' field is in a range of 1 to 10")
    private Integer score;
    private List<ChoiceOption> options;

    public ChoiceQuestion() {
    }

    public ChoiceQuestion(Long choiceQuestionId, Integer sequenceNumber, String title, Integer type, Integer score, List<ChoiceOption> options) {
        this.choiceQuestionId = choiceQuestionId;
        this.sequenceNumber = sequenceNumber;
        this.title = title;
        this.type = type;
        this.score = score;
        this.options = options;
    }
}
