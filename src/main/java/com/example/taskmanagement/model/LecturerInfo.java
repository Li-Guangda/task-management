package com.example.taskmanagement.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class LecturerInfo {
    @NotNull(message = "Lecturer ID cannot be null")
    @Min(value = 0, message = "Lecturer ID must be equal to or greater than 0")
    private Long lecturerId;
    private String university;
    private String position;
    private String avatar;
    private String name;
    private String gender;
    private String intro;
    public LecturerInfo(Long lecturerId, String university, String position, String avatar, String name, String gender, String intro) {
        this.lecturerId = lecturerId;
        this.university = university;
        this.position = position;
        this.avatar = avatar;
        this.name = name;
        this.gender = gender;
        this.intro = intro;
    }
    public Long getLecturerId() {
        return lecturerId;
    }
    public void setLecturerId(Long lecturerId) {
        this.lecturerId = lecturerId;
    }
    public String getUniversity() {
        return university;
    }
    public void setUniversity(String university) {
        this.university = university;
    }
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
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
