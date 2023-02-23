package com.example.taskmanagement.service.impl;

import com.example.taskmanagement.mapper.CommentMapper;
import com.example.taskmanagement.model.Comment;
import com.example.taskmanagement.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService implements ICommentService {

    private CommentMapper commentMapper;

    @Override
    public Integer addComment(Comment comment) {
        return commentMapper.addComment(
                comment.getClassId(),
                comment.getUserId(),
                comment.getContent()
        );
    }

    @Override
    public Integer deleteAllCommentsOfClassroom(Long classId) {
        return commentMapper.deleteAllCommentsOfClassroom(classId);
    }

    @Override
    public Integer deleteComment(Long commentId) {
        return commentMapper.deleteCommentByCommentId(commentId);
    }

    @Override
    public Integer updateComment(Comment comment) {
        return commentMapper.updateCommentByCommentId(
                comment.getCommentId(),
                comment.getUserId(),
                comment.getClassId(),
                comment.getContent()
        );
    }

    @Override
    public List<Comment> getAllCommentsOfClass(Long classId) {
        return commentMapper.getAllCommentsOfClassroom(classId);
    }

    @Override
    public Comment getComment(Long commentId) {
        return commentMapper.getCommentByCommentId(commentId);
    }

    @Autowired
    public void setCommentMapper(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }
}
