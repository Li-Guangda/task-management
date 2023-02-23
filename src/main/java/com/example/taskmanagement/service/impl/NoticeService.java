package com.example.taskmanagement.service.impl;

import com.example.taskmanagement.mapper.NoticeMapper;
import com.example.taskmanagement.model.Notice;
import com.example.taskmanagement.service.INoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService implements INoticeService {

    private NoticeMapper noticeMapper;

    @Override
    public Integer addNotice(Notice notice) {
        return noticeMapper.addNotice(
            notice.getClassId(),
            notice.getContent(),
            notice.getDate()
        );
    }

    @Override
    public Integer deleteAllNotices() {
        return noticeMapper.deleteAllNotices();
    }

    @Override
    public Integer deleteNotice(Long noticeId) {
        return noticeMapper.deleteNoticeByNoticeId(noticeId);
    }

    @Override
    public Integer updateNotice(Notice notice) {
        return noticeMapper.updateNoticeByNoticeId(
                notice.getNoticeId(),
                notice.getClassId(),
                notice.getContent(),
                notice.getDate()
        );
    }

    @Override
    public List<Notice> getAllNoticesOfClassroom(Long classId) {
        return noticeMapper.getNoticesByClassId(classId);
    }

    @Override
    public Notice getNotice(Long noticeId) {
        return noticeMapper.getNoticeByNoticeId(noticeId);
    }

    @Autowired
    public void setNoticeMapper(NoticeMapper noticeMapper) {
        this.noticeMapper = noticeMapper;
    }
}
