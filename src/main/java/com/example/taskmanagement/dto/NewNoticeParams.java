package com.example.taskmanagement.dto;

import lombok.Data;

@Data
public class NewNoticeParams {
    private Long classId;
    private String content;

    public NewNoticeParams() {
    }

    public NewNoticeParams(Long classId, String content) {
        this.classId = classId;
        this.content = content;
    }
}
