package com.example.taskmanagement.controller.v1;

import com.example.taskmanagement.controller.response.Result;
import com.example.taskmanagement.model.Notice;
import com.example.taskmanagement.service.impl.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class NoticeController {

    private NoticeService noticeService;

    @PostMapping("/classroom/{classId}/notice")
    public Result addNotice(@PathVariable Long classId, @RequestBody @Validated Notice notice) {
        noticeService.addNotice(notice);
        return Result.onlyMessage("Added notice successfully");
    }

    @DeleteMapping("/classroom/notices")
    public Result deleteAllNotice() {
        noticeService.deleteAllNotices();
        return Result.onlyMessage("Deleted all notices successfully");
    }

    @DeleteMapping("/classroom/{classId}/notice/{noticeId}")
    public Result deleteNotice(@PathVariable Long classId, @PathVariable Long noticeId) {
        noticeService.deleteNotice(noticeId);
        return Result.onlyMessage("Deleted the notice successfully");
    }

    @PutMapping("/classroom/{classId}/notice/{noticeId}")
    public Result updateNotice(@PathVariable Long classId, @PathVariable Long noticeId, @RequestBody Notice notice) {
        noticeService.updateNotice(notice);
        return Result.onlyMessage("Updated the notice successfully");
    }

    @GetMapping("/classroom/{classId}/notices")
    public Result getNoticesOfClassroom(@PathVariable Long classId) {
        List<Notice> notices = noticeService.getAllNoticesOfClassroom(classId);
        return new Result("Got all notices", notices);
    }

    @GetMapping("/classroom/{classId}/notice/{noticeId}")
    public Result getNotice(@PathVariable Long classId, @PathVariable Long noticeId) {
        Notice notice = noticeService.getNotice(noticeId);
        return new Result("Got the notice", notice);
    }

    @Autowired
    public void setNoticeService(NoticeService noticeService) {
        this.noticeService = noticeService;
    }
}
