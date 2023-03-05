package com.example.taskmanagement.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChoiceOption {
    private Long choiceOptionId;
    @Min(value = 1, message = "The sequence number must be greater than 0")
    private Integer sequenceNumber;
    @NotBlank(message = "The content content can not be blank")
    private String content;
    @NotNull(message = "'isAnswer' field is boolean type")
    private boolean isAnswer;

    public boolean getIsAnswer() {
        return isAnswer;
    }

    public void setIsAnswer(boolean answer) {
        isAnswer = answer;
    }

    public ChoiceOption() {
    }

    public ChoiceOption(Long choiceOptionId, Integer sequenceNumber, String content, boolean isAnswer) {
        this.choiceOptionId = choiceOptionId;
        this.sequenceNumber = sequenceNumber;
        this.content = content;
        this.isAnswer = isAnswer;
    }
}
