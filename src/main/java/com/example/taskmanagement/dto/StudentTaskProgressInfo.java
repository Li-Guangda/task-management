package com.example.taskmanagement.dto;

import com.example.taskmanagement.dto.StudentInfo;
import lombok.Data;

import java.util.List;

@Data
public class StudentTaskProgressInfo {
    private Long taskId;
    private List<StudentInfo> StudentTaskIsChecked;
    private List<StudentInfo> StudentTaskIsNotChecked;
    private List<StudentInfo> StudentTaskIsNotFinished;
}

