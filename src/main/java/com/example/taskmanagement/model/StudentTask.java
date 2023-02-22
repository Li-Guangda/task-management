package com.example.taskmanagement.model;

public class StudentTask {

    private Long studentId;
    private Long taskId;
    private Integer score;
    private String remark;
    private String attachment;
    private String content;

    public StudentTask(Long studentId, Long taskId, Integer score, String remark, String attachment, String content) {
        this.studentId = studentId;
        this.taskId = taskId;
        this.score = score;
        this.remark = remark;
        this.attachment = attachment;
        this.content = content;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
