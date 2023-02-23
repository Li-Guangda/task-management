package com.example.taskmanagement.service;

import com.example.taskmanagement.model.Comment;

import java.util.List;

public interface ICommentService {

    Integer addComment(Comment comment);
    Integer deleteAllCommentsOfClassroom(Long classId);
    Integer deleteComment(Long commentId);
    Integer updateComment(Comment comment);
    List<Comment> getAllCommentsOfClass(Long classId);
    Comment getComment(Long commentId);
}
