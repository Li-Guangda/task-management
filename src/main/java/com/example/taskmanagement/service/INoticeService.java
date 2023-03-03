package com.example.taskmanagement.service;


import com.example.taskmanagement.dto.EditNoticeParams;
import com.example.taskmanagement.dto.NewNoticeParams;
import com.example.taskmanagement.dto.NoticeInfo;

import java.util.List;

public interface INoticeService {

    void newNoticeToClassroom(NewNoticeParams newNoticeParams);
    void deleteNoticeFromClassroom(Long noticeId);
    void editNoticeOfClassroom(EditNoticeParams editNoticeParams);
    List<NoticeInfo> getNoticesOfClassroom(Long classroomId);
}
