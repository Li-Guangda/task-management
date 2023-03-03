package com.example.taskmanagement.dto;

import lombok.Data;

@Data
public class StudentInfo {

    private Long studentId;
    private String name;
    private String avatar;
    private boolean joined;

    public StudentInfo() {}

    public StudentInfo(Long studentId, String name, String avatar, boolean joined) {
        this.studentId = studentId;
        this.name = name;
        this.avatar = avatar;
        this.joined = joined;
    }
}