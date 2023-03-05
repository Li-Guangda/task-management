package com.example.taskmanagement.dto;

import com.example.taskmanagement.dto.StudentInfo;
import lombok.Data;

import java.util.List;

@Data
public class StudentTaskProgressInfo {
    private Long taskId;
    private Long userId;
    private String name;
    private String avatar;
    private String taskStatus;

    public StudentTaskProgressInfo(Long taskId, Long userId, String name, String avatar, String taskStatus) {
        this.taskId = taskId;
        this.userId = userId;
        this.name = name;
        this.avatar = avatar;
        this.taskStatus = taskStatus;
    }
}

