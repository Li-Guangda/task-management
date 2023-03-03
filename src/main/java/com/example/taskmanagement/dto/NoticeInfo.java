package com.example.taskmanagement.dto;

import lombok.Data;

import java.util.Date;

@Data
public class NoticeInfo {
    private Long noticeId;
    private Long classId;
    private String content;
    private Date date;

    public NoticeInfo() {
    }

    public NoticeInfo(Long noticeId, Long classId, String content, Date date) {
        this.noticeId = noticeId;
        this.classId = classId;
        this.content = content;
        this.date = date;
    }
}
