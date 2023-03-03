package com.example.taskmanagement.dto;

import lombok.Data;

import java.util.List;

@Data
public class ClassroomTaskInfo {

    private List<TaskInfo> tasks;
}
