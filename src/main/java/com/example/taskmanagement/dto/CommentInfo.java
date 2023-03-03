package com.example.taskmanagement.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;

@Data
public class CommentInfo {
    @Min(value = 0, message = "The comment id must be greater than 0")
    private Long commentId;
    @Min(value = 0, message = "The class id must be greater than 0")
    private Long classId;
    @NotBlank(message = "The content cannot be blank")
    private String content;
    private Date date;
    private UserInfo commentUser;

    public CommentInfo() {}

    public CommentInfo(Long commentId, Long classId, String content, Date date, UserInfo commentUser) {
        this.commentId = commentId;
        this.classId = classId;
        this.content = content;
        this.date = date;
        this.commentUser = commentUser;
    }
}