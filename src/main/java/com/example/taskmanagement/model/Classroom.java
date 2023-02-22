package com.example.taskmanagement.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class Classroom {

    @Min(value = 0, message = "Class ID must equal to or greater than 0")
    private Long classId;
    @Min(value = 0, message = "Lecturer ID must equal to or greater than 0")
    private Long lecturerId;
    @NotBlank(message = "Class name cannot be blank")
    private String className;
    private String classCode;

    public Classroom(Long classId, Long lecturerId, String className, String classCode) {
        this.classId = classId;
        this.lecturerId = lecturerId;
        this.className = className;
        this.classCode = classCode;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Long getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(Long lecturerId) {
        this.lecturerId = lecturerId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }
}
