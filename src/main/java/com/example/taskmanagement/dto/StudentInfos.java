package com.example.taskmanagement.dto;

import lombok.Data;

import java.util.List;

@Data
public class StudentInfos {

    private List<StudentInfo> studentInfos;

    public StudentInfos() {}

    public StudentInfos(List<StudentInfo> studentInfos) {
        this.studentInfos = studentInfos;
    }
}
