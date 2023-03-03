package com.example.taskmanagement.dto;

import lombok.Data;

@Data
public class StudentDetailedInfo {

    private Long studentId;
    private String university;
    private String studentNumber;
    private String avatar;
    private String name;
    private String gender;
    private String intro;

    public StudentDetailedInfo() {}

    public StudentDetailedInfo(Long studentId, String university, String studentNumber, String avatar,
                               String name, String gender, String intro) {
        this.studentId = studentId;
        this.university = university;
        this.studentNumber = studentNumber;
        this.avatar = avatar;
        this.name = name;
        this.gender = gender;
        this.intro = intro;
    }
}
