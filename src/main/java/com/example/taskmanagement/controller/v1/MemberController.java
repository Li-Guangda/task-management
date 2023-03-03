package com.example.taskmanagement.controller.v1;

import com.example.taskmanagement.dto.Result;
import com.example.taskmanagement.dto.StudentInfo;
import com.example.taskmanagement.service.impl.MemberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class MemberController {

    private MemberServiceImpl studentServiceImpl;

    /**
     * 获取当前已加入该班级的学生信息
     * @param classroomId
     * @param token
     * @return
     */
    @GetMapping("/classroom/{classroomId}/joinedStudents")
    @PreAuthorize("@preAuthorizeHelper.isClassroomStudent(#classroomId, #token) or " +
                    "@preAuthorizeHelper.isClassroomLecturer(#classroomId, #token)")
    public Result getStudentsJoinedOfClassroom(@PathVariable Long classroomId,
                                               @RequestHeader("Authorization") String token) {
        List<StudentInfo> studentInfos = studentServiceImpl.getStudentsJoinedOfClassroom(classroomId);
        return new Result("Got the students joining the classroom successfully", studentInfos);
    }

    /**
     * 获取当前待加入该班级的学生信息
     * @param classroomId
     * @param token
     * @return
     */
    @GetMapping("/classroom/{classroomId}/toBeJoinedStudents")
    @PreAuthorize("@preAuthorizeHelper.isClassroomLecturer(#classroomId, #token)")
    public Result getStudentsTobeJoinedOfClassroom(@PathVariable Long classroomId,
                                                   @RequestHeader("Authorization") String token) {
        List<StudentInfo> studentInfos = studentServiceImpl.getStudentsToBeJoinedOfClassroom(classroomId);
        return new Result("Got the students to be joined in the classroom successfully", studentInfos);
    }

    /**
     * 从当前班级中删除学生
     * @param classroomId
     * @param userId
     * @param token
     * @return
     */
    @DeleteMapping("/classroom/{classroomId}/students/{userId}")
    @PreAuthorize("@preAuthorizeHelper.isClassroomLecturer(#classroomId, #token)")
    public Result deleteStudentFromClassroom(@PathVariable Long classroomId,
                                             @PathVariable Long userId,
                                             @RequestHeader("Authorization") String token) {
        studentServiceImpl.deleteStudentFromClassroom(classroomId, userId);
        return Result.onlyMessage("Deleted the student successfully");
    }

    /**
     * 教师接受学生入班请求
     * @param classroomId
     * @param lecturerId
     * @param studentId
     * @param token
     * @return
     */
    @PutMapping("/classroom/{classroomId}/lecturer/{lecturerId}/students/{studentId}")
    @PreAuthorize("@preAuthorizeHelper.isClassroomLecturer(#classroomId, #token) " +
                  "")
    public Result acceptStudent(@PathVariable Long classroomId,
                                @PathVariable Long lecturerId,
                                @PathVariable Long studentId,
                                @RequestHeader("Authorization") String token) {
        studentServiceImpl.acceptStudent(classroomId, studentId);
        return Result.onlyMessage("Accepted the student successfully");
    }

    @Autowired
    public void setStudentServiceImpl(MemberServiceImpl studentServiceImpl) {
        this.studentServiceImpl = studentServiceImpl;
    }
}
