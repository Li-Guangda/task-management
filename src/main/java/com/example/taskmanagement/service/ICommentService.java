package com.example.taskmanagement.service;


import com.example.taskmanagement.dto.CommentInfo;
import com.example.taskmanagement.dto.NewCommentParams;

import java.util.List;

public interface ICommentService {

    List<CommentInfo> getCommentsOfClassroom(Long classroomId);
    void newCommentToClassroom(NewCommentParams newCommentParams);
}
