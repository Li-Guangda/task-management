package com.example.taskmanagement.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class ChoiceQuestion {
    @Min(value = 1, message = "The sequence number must be greater than 0")
    private Integer sequenceNumber;
    @NotBlank(message = "The question title can not be blank")
    private String title;
    @Range(min = 0, max = 1, message = "'type' field can only be 0 or 1")
    private Integer type;
    @Range(min = 1, max = 10, message = "'score' field is in a range of 1 to 10")
    private Integer score;
    private ChoiceOption[] options;
}
