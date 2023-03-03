package com.example.taskmanagement.controller.v1;

import com.example.taskmanagement.dto.CommentInfo;
import com.example.taskmanagement.dto.NewCommentParams;
import com.example.taskmanagement.dto.Result;
import com.example.taskmanagement.service.impl.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CommentController {

    private CommentServiceImpl commentServiceImpl;

    /**
     * 班级成员获取该班所有评论
     * @param classroomId
     * @param token
     * @return
     */
    @GetMapping("/classroom/{classroomId}/comments")
    @PreAuthorize("@preAuthorizeHelper.isClassroomMember(#classroomId, #token)")
    public Result getCommentsOfClassroom(@PathVariable Long classroomId,
                                         @RequestHeader("Authorization") String token) {
        List<CommentInfo> commentInfos =commentServiceImpl.getCommentsOfClassroom(classroomId);
        return new Result("Got all comments of the classroom successfully", commentInfos);
    }

    /**
     * 班级成员发表评论
     * @param classroomId
     * @param newCommentParams
     * @param token
     * @return
     */
    @PostMapping("/classroom/{classroomId}/comment")
    @PreAuthorize("@preAuthorizeHelper.isClassroomMember(#classroomId, #token)")
    public Result newCommentToClassroom(@PathVariable Long classroomId,
                                        @RequestBody NewCommentParams newCommentParams,
                                        @RequestHeader("Authorization") String token) {
        commentServiceImpl.newCommentToClassroom(newCommentParams);
        return Result.onlyMessage("Made one new comment successfully");
    }

    @Autowired
    public void setCommentServiceImpl(CommentServiceImpl commentServiceImpl) {
        this.commentServiceImpl = commentServiceImpl;
    }
}
