package com.example.taskmanagement.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class Comment {

    @Min(value = 0, message = "The comment ID must equal to or greater than 0")
    private Long commentId;
    @Min(value = 0, message = "The class ID must equal to or greater than 0")
    private Long classId;
    @Min(value = 0, message = "The user ID must equal to or greater than 0")
    private Long userId;
    @NotNull(message = "The comment content cannot be null")
    private String content;
    private Date date;

    public Comment(Long commentId, Long classId, Long userId, String content, Date date) {
        this.commentId = commentId;
        this.classId = classId;
        this.userId = userId;
        this.content = content;
        this.date = date;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
