package com.example.taskmanagement.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClassroomParams {
    @Min(value = 0, message = "The lecturer id must be greater than 0")
    private Long lecturerId;
    @NotBlank(message = "The class name can not be blank")
    private String className;

    public ClassroomParams() {}

    public ClassroomParams(Long lecturerId, String className) {
        this.lecturerId = lecturerId;
        this.className = className;
    }
}
