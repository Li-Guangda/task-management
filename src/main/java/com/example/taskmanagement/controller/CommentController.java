package com.example.taskmanagement.controller;

import com.example.taskmanagement.controller.response.Result;
import com.example.taskmanagement.model.Comment;
import com.example.taskmanagement.service.impl.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CommentController {

    private CommentService commentService;

    @PostMapping("/classroom/{classId}/comment")
    public Result addComment(@PathVariable Long classId, @RequestBody @Validated Comment comment) {
        commentService.addComment(comment);
        return Result.onlyMessage("Added comment successfully");
    }

    @DeleteMapping("/classroom/{classId}/comments")
    public Result deleteAllCommentsOfClassroom(@PathVariable Long classId) {
        commentService.deleteAllCommentsOfClassroom(classId);
        return Result.onlyMessage("Deleted all comments successfully");
    }

    @DeleteMapping("/classroom/{classId}/comment/{commentId}")
    public Result deleteComment(@PathVariable Long classId, @PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return Result.onlyMessage("Deleted comment successfully");
    }

    @PutMapping("/classroom/{classId}/comment/{commentId}")
    public Result updateComment(@PathVariable Long classId, @PathVariable Long commentId, @RequestBody @Validated Comment comment) {
        commentService.updateComment(comment);
        return Result.onlyMessage("Updated comment successfully");
    }

    @GetMapping("/classroom/{classId}/comments")
    public Result getAllCommentsOfClassroom(@PathVariable Long classId) {
        List<Comment> comments = commentService.getAllCommentsOfClass(classId);
        return new Result("Got all comments successfully", comments);
    }

    @GetMapping("/classroom/{classId}/comment/{commentId}")
    public Result getComment(@PathVariable Long classId, @PathVariable Long commentId) {
        Comment comment = commentService.getComment(commentId);
        return new Result("Got comment successfully", comment);
    }

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }
}
