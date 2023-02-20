package com.example.taskmanagement.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class StudentInfo {
    @NotNull
    @Min(value = 0, message = "StudentId must be equal to or greater than 0")
    private Long studentId;
    private String university;
    private String studentNumber;
    private String avatar;
    private String name;
    private String gender;
    private String intro;

    public StudentInfo(Long studentId, String university, String studentNumber, String avatar, String name, String gender, String intro) {
        this.studentId = studentId;
        this.university = university;
        this.studentNumber = studentNumber;
        this.avatar = avatar;
        this.name = name;
        this.gender = gender;
        this.intro = intro;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
