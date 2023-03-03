package com.example.taskmanagement.dto;

import lombok.Data;

@Data
public class LecturerDetailedInfo {

    private Long lecturerId;
    private String university;
    private String position;
    private String avatar;
    private String name;
    private String gender;
    private String intro;

    public LecturerDetailedInfo() {
    }

    public LecturerDetailedInfo(Long lecturerId, String university, String position, String avatar,
                                String name, String gender, String intro) {
        this.lecturerId = lecturerId;
        this.university = university;
        this.position = position;
        this.avatar = avatar;
        this.name = name;
        this.gender = gender;
        this.intro = intro;
    }
}
