package com.example.taskmanagement.dto;

import lombok.Data;

import java.util.List;

@Data
public class StudentTaskProgressInfos {

    private List<StudentTaskProgressInfo> studentTaskProgressInfos;

    public StudentTaskProgressInfos(List<StudentTaskProgressInfo> studentTaskProgressInfos) {
        this.studentTaskProgressInfos = studentTaskProgressInfos;
    }
}
