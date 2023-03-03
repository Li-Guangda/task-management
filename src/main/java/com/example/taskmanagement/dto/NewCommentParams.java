package com.example.taskmanagement.dto;

import lombok.Data;

@Data
public class NewCommentParams {
    private Long classId;
    private Long userId;
    private String content;

    public NewCommentParams() {
    }

    public NewCommentParams(Long classId, Long userId, String content) {
        this.classId = classId;
        this.userId = userId;
        this.content = content;
    }
}
