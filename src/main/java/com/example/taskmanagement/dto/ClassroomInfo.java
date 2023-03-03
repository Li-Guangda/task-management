package com.example.taskmanagement.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ClassroomInfo {
    @Min(value = 1, message = "The class id must be greater than 0")
    private Long classId;
    @Min(value = 1, message = "The lecturer id must be greater than 0")
    private Long lecturerId;
    @NotBlank(message = "The class name can not be blank")
    private String className;
    @Length(min = 6, max = 6, message = "The class code must be 6 in length")
    private String classCode;
    @Min(value = 0, message = "The number of student is at least 0")
    private Integer studentTotalCount;

    public ClassroomInfo() {}

    public ClassroomInfo(Long classId, Long lecturerId, String className, String classCode, Integer studentTotalCount) {
        this.classId = classId;
        this.lecturerId = lecturerId;
        this.className = className;
        this.classCode = classCode;
        this.studentTotalCount = studentTotalCount;
    }
}
