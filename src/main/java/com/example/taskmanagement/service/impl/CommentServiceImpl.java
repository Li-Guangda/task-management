package com.example.taskmanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.taskmanagement.dao.CommentMapper;
import com.example.taskmanagement.dao.LecturerInfoMapper;
import com.example.taskmanagement.dao.StudentInfoMapper;
import com.example.taskmanagement.dto.CommentInfo;
import com.example.taskmanagement.dto.NewCommentParams;
import com.example.taskmanagement.dto.UserInfo;
import com.example.taskmanagement.po.CommentPO;
import com.example.taskmanagement.po.LecturerInfoPO;
import com.example.taskmanagement.po.StudentInfoPO;
import com.example.taskmanagement.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements ICommentService {

    private CommentMapper commentMapper;
    private StudentInfoMapper studentInfoMapper;
    private LecturerInfoMapper lecturerInfoMapper;

    /**
     * 班级成员获取班级评论
     * @param classroomId
     * @return
     */

    public List<CommentInfo> getCommentsOfClassroom(Long classroomId) {
        QueryWrapper<CommentPO> commentPOQueryWrapper = new QueryWrapper<>();
        commentPOQueryWrapper.eq("classroom_id", classroomId);
        List<CommentPO> commentPOS = commentMapper.selectList(commentPOQueryWrapper);

        List<CommentInfo> commentInfos = new ArrayList<>();
        for (CommentPO commentPO: commentPOS) {
            UserInfo commentUser = null;

            QueryWrapper<LecturerInfoPO> lecturerInfoPOQueryWrapper = new QueryWrapper<>();
            lecturerInfoPOQueryWrapper.eq("lecturer_id", commentPO.getUserId());

            QueryWrapper<StudentInfoPO> studentInfoPOQueryWrapper = new QueryWrapper<>();
            studentInfoPOQueryWrapper.eq("student_id", commentPO.getUserId());

            if (lecturerInfoMapper.exists(lecturerInfoPOQueryWrapper)) {
                LecturerInfoPO lecturerInfoPO = lecturerInfoMapper.selectById(commentPO.getUserId());
                commentUser = new UserInfo(
                        lecturerInfoPO.getLecturerId(),
                        lecturerInfoPO.getName(),
                        lecturerInfoPO.getAvatar()
                );
            } else if (studentInfoMapper.exists(studentInfoPOQueryWrapper)) {
                StudentInfoPO studentInfoPO = studentInfoMapper.selectById(commentPO.getUserId());
                commentUser = new UserInfo(
                        studentInfoPO.getStudentId(),
                        studentInfoPO.getName(),
                        studentInfoPO.getAvatar()
                );
            }
            commentInfos.add(new CommentInfo(
                    commentPO.getCommentId(),
                    commentPO.getClassroomId(),
                    commentPO.getContent(),
                    commentPO.getDate(),
                    commentUser
            ));
        }

        return commentInfos;
    }

    /**
     * 班级成员发布评论
     * @param newCommentParams
     */
    @Override
    public void newCommentToClassroom(NewCommentParams newCommentParams) {
        CommentPO commentPO = new CommentPO();
        commentPO.setClassroomId(newCommentParams.getClassId());
        commentPO.setUserId(newCommentParams.getUserId());
        commentPO.setContent(newCommentParams.getContent());
        commentMapper.insert(commentPO);
    }

    @Autowired
    public void setCommentMapper(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    @Autowired
    public void setStudentInfoMapper(StudentInfoMapper studentInfoMapper) {
        this.studentInfoMapper = studentInfoMapper;
    }

    @Autowired
    public void setLecturerInfoMapper(LecturerInfoMapper lecturerInfoMapper) {
        this.lecturerInfoMapper = lecturerInfoMapper;
    }
}
