package com.example.taskmanagement.controller.v1;

import com.example.taskmanagement.dto.EditNoticeParams;
import com.example.taskmanagement.dto.NewNoticeParams;
import com.example.taskmanagement.dto.NoticeInfo;
import com.example.taskmanagement.dto.Result;
import com.example.taskmanagement.service.impl.NoticeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class NoticeController {

    private NoticeServiceImpl noticeServiceImpl;

    /**
     * 教师发布新公告
     * @param classroomId
     * @param newNoticeParams
     * @param token
     * @return
     */
    @PostMapping("/classroom/{classroomId}/notice")
    @PreAuthorize("@preAuthorizeHelper.isClassroomLecturer(#classroomId, #token)")
    public Result newNoticeToClassroom(@PathVariable Long classroomId,
                                       @RequestBody NewNoticeParams newNoticeParams,
                                       @RequestHeader("Authorization") String token) {
        noticeServiceImpl.newNoticeToClassroom(newNoticeParams);
        return Result.onlyMessage("Published a new notice successfully");
    }

    /**
     * 教师删除公告
     * @param classroomId
     * @param noticeId
     * @param token
     * @return
     */
    @DeleteMapping("/classroom/{classroomId}/notices/{noticeId}")
    @PreAuthorize("@preAuthorizeHelper.isClassroomLecturer(#classroomId, #token)")
    public Result deleteNoticeFromClassroom(@PathVariable Long classroomId,
                                            @PathVariable Long noticeId,
                                            @RequestHeader("Authorization") String token) {
        noticeServiceImpl.deleteNoticeFromClassroom(noticeId);
        return Result.onlyMessage("Deleted the notice successfully");
    }

    /**
     * 教师编辑公告
     * @param classroomId
     * @param noticeId
     * @param editNoticeParams
     * @param token
     * @return
     */
    @PutMapping("/classroom/{classroomId}/notices/{noticeId}")
    @PreAuthorize("@preAuthorizeHelper.isClassroomLecturer(#classroomId, #token)")
    public Result editNoticeOfClassroom(@PathVariable Long classroomId,
                                        @PathVariable Long noticeId,
                                        @RequestBody EditNoticeParams editNoticeParams,
                                        @RequestHeader("Authorization") String token) {
        noticeServiceImpl.editNoticeOfClassroom(editNoticeParams);
        return Result.onlyMessage("Update the notice successfully");
    }

    /**
     * 班级成员获取当前班级公告
     * @param classroomId
     * @param token
     * @return
     */
    @GetMapping("/classroom/{classroomId}/notices")
    @PreAuthorize("@preAuthorizeHelper.isClassroomLecturer(#classroomId, #token) or" +
                    "@preAuthorizeHelper.isClassroomStudent(#classroomId, #token)")
    public Result getNoticesOfClassroom(@PathVariable Long classroomId,
                                        @RequestHeader("Authorization") String token) {
        List<NoticeInfo> noticeInfos = noticeServiceImpl.getNoticesOfClassroom(classroomId);
        return new Result("Got all notices of the classroom successfully", noticeInfos);
    }

    @Autowired
    public void setNoticeServiceImpl(NoticeServiceImpl noticeServiceImpl) {
        this.noticeServiceImpl = noticeServiceImpl;
    }
}
