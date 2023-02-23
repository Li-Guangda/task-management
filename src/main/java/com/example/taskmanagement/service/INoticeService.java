package com.example.taskmanagement.service;

import com.example.taskmanagement.model.Notice;

import java.util.Date;
import java.util.List;

public interface INoticeService {

    Integer addNotice(Notice notice);
    Integer deleteAllNotices();
    Integer deleteNotice(Long noticeId);
    Integer updateNotice(Notice notice);
    List<Notice> getAllNoticesOfClassroom(Long classId);
    Notice getNotice(Long noticeId);
}
