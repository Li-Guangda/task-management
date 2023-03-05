package com.example.taskmanagement.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TaskInfo {
    private Long taskId;
    private Long classroomId;
    private String taskTitle;
    private String taskDesc;
    private Date dateStart;
    private Date dateEnd;

    private Integer studentSubmittedCount;

    public TaskInfo() {}

    public TaskInfo(Long taskId, Long classroomId, String taskTitle, String taskDesc,
                    Date dateStart, Date dateEnd, Integer studentSubmittedCount) {
        this.taskId = taskId;
        this.classroomId = classroomId;
        this.taskTitle = taskTitle;
        this.taskDesc = taskDesc;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.studentSubmittedCount = studentSubmittedCount;
    }
}
