package com.example.taskmanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.taskmanagement.dao.NoticeMapper;
import com.example.taskmanagement.dto.EditNoticeParams;
import com.example.taskmanagement.dto.NewNoticeParams;
import com.example.taskmanagement.dto.NoticeInfo;
import com.example.taskmanagement.po.NoticePO;
import com.example.taskmanagement.service.INoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoticeServiceImpl implements INoticeService {

    private NoticeMapper noticeMapper;

    @Override
    public void newNoticeToClassroom(NewNoticeParams newNoticeParams) {
        NoticePO noticePO = new NoticePO();
        noticePO.setClassroomId(newNoticeParams.getClassId());
        noticePO.setContent(newNoticeParams.getContent());
        noticeMapper.insert(noticePO);
    }

    @Override
    public void deleteNoticeFromClassroom(Long noticeId) {
        noticeMapper.deleteById(noticeId);
    }

    @Override
    public void editNoticeOfClassroom(EditNoticeParams editNoticeParams) {
        NoticePO noticePO = new NoticePO();
        noticePO.setNoticeId(editNoticeParams.getNoticeId());
        noticePO.setContent(editNoticeParams.getContent());
        noticeMapper.updateById(noticePO);
    }

    @Override
    public List<NoticeInfo> getNoticesOfClassroom(Long classroomId) {
        QueryWrapper<NoticePO> noticePOQueryWrapper = new QueryWrapper<>();
        noticePOQueryWrapper.eq("classroom_id", classroomId);
        List<NoticePO> noticePOS = noticeMapper.selectList(noticePOQueryWrapper);

        List<NoticeInfo> noticeInfos = new ArrayList<>();
        for (NoticePO noticePO: noticePOS) {
            noticeInfos.add(new NoticeInfo(
                    noticePO.getNoticeId(),
                    noticePO.getClassroomId(),
                    noticePO.getContent(),
                    noticePO.getDate()
            ));
        }
        return noticeInfos;
    }

    @Autowired
    public void setNoticeMapper(NoticeMapper noticeMapper) {
        this.noticeMapper = noticeMapper;
    }
}
