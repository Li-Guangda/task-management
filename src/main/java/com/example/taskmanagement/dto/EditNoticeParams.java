package com.example.taskmanagement.dto;

import lombok.Data;

@Data
public class EditNoticeParams {
    private Long noticeId; // 待改公告id
    private String content; // 内容

    public EditNoticeParams() {
    }

    public EditNoticeParams(Long noticeId, String content) {
        this.noticeId = noticeId;
        this.content = content;
    }
}
