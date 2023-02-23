package com.example.taskmanagement.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class Notice {

    @Min(value = 0, message = "The notice ID must equal to or greater than 0")
    private Long noticeId;
    @Min(value = 0, message = "The class ID must equal to or greater than 0")
    private Long classId;
    @NotNull(message = "The content cannot be null")
    private String content;
    private Date date;

    public Notice(Long noticeId, Long classId, String content, Date date) {
        this.noticeId = noticeId;
        this.classId = classId;
        this.content = content;
        this.date = date;
    }

    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
