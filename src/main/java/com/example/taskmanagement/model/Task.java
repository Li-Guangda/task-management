package com.example.taskmanagement.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;

public class Task {

    @Min(value = 0, message = "Task ID must equal to or greater than 0")
    private Long taskId;

    @Min(value = 0, message = "Class ID must equal to or greater than 0")
    private Long classId;

    @NotBlank(message = "Task title cannot be blank")
    private String taskTitle;

    @NotBlank(message = "Task description cannot be blank")
    private String taskDesc;

    private Date dateStart;

    private Date dateEnd;

    public Task(Long taskId, Long classId, String taskTitle, String taskDesc, Date dateStart, Date dateEnd) {
        this.taskId = taskId;
        this.classId = classId;
        this.taskTitle = taskTitle;
        this.taskDesc = taskDesc;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }
}
