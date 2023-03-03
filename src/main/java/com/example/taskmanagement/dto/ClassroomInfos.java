package com.example.taskmanagement.dto;

import lombok.Data;

import java.util.List;

@Data
public class ClassroomInfos {
    private List<ClassroomInfo> classroomInfos;

    public ClassroomInfos() {}

    public ClassroomInfos(List<ClassroomInfo> classroomInfos) {
        this.classroomInfos = classroomInfos;
    }
}
