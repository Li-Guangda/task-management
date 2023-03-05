package com.example.taskmanagement.controller.v1;

import com.example.taskmanagement.dto.Result;
import com.example.taskmanagement.dto.StudentInfos;
import com.example.taskmanagement.service.exception.ClassroomNotFoundException;
import com.example.taskmanagement.service.exception.StudentAlreadyInClassroomException;
import com.example.taskmanagement.service.exception.StudentNotInClassroomException;
import com.example.taskmanagement.service.impl.MemberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
public class MemberController {

    private MemberServiceImpl studentServiceImpl;

    /**
     * 获取当前已加入或待加入该班级的学生信息
     * @param classroomId
     * @param token
     * @return
     */
    @GetMapping("/classrooms/{classroomId}/students")
    @PreAuthorize("@preAuthorizeHelper.isClassroomStudent(#classroomId, #token) or " +
                    "@preAuthorizeHelper.isClassroomLecturer(#classroomId, #token)")
    public Result getStudentsJoinedOfClassroom(@PathVariable Long classroomId,
                                               @RequestParam(name = "status", required = false) String status,
                                               @RequestHeader("Authorization") String token) {
        StudentInfos studentInfos;
        String message;
        if (status == null || status.equals("joined")) {
            studentInfos = studentServiceImpl.getStudentsJoinedOfClassroom(classroomId);
            message = "Successfully got the students in the classroom";
        } else if (status.equals("to-be-joined")) {
            studentInfos = studentServiceImpl.getStudentsToBeJoinedOfClassroom(classroomId);
            message = "Successfully got the students to be joined in the classroom";
        } else {
            studentInfos = studentServiceImpl.getStudentsJoinedOfClassroom(classroomId);
            message = "Successfully got the students in the classroom";
        }
        return new Result(message, studentInfos);
    }

    /**
     * 从当前班级中删除学生
     * @param classroomId
     * @param userId
     * @param token
     * @return
     */
    @DeleteMapping("/classrooms/{classroomId}/students/{userId}")
    @PreAuthorize("@preAuthorizeHelper.isClassroomLecturer(#classroomId, #token)")
    public ResponseEntity<Result> deleteStudentFromClassroom(@PathVariable Long classroomId,
                                                             @PathVariable Long userId,
                                                             @RequestHeader("Authorization") String token) {
        String message;
        Integer code;
        try {
            studentServiceImpl.deleteStudentFromClassroom(classroomId, userId);
            code = 200;
            message = "Successfully deleted the student from the classroom";
        } catch (ClassroomNotFoundException e) {
            code = 400;
            message = "The classroom doesn't exist";
        } catch (StudentNotInClassroomException e) {
            code = 400;
            message = "The student isn't in the classroom";
        }
        return ResponseEntity.status(code).body(Result.onlyMessage(message));
    }

    /**
     * 教师接受学生入班请求
     * @param classroomId
     * @param studentId
     * @param token
     * @return
     */
    @PutMapping("/classrooms/{classroomId}/students/{studentId}")
    @PreAuthorize("@preAuthorizeHelper.isClassroomLecturer(#classroomId, #token)")
    public ResponseEntity<Result> acceptStudent(@PathVariable Long classroomId,
                                                @PathVariable Long studentId,
                                                @RequestHeader("Authorization") String token) {
        Integer code;
        String message;
        try {
            studentServiceImpl.acceptStudent(classroomId, studentId);
            code = 200;
            message = "Successfully accepted the student";
        } catch (ClassroomNotFoundException e) {
            code = 400;
            message = "The classroom doesn't exist";
        } catch (StudentAlreadyInClassroomException e) {
            code = 400;
            message = "The student is already in the classroom";
        } catch (StudentNotInClassroomException e) {
            code = 400;
            message = "The student was not found";
        }
        return ResponseEntity.status(code).body(Result.onlyMessage(message));
    }

    @Autowired
    public void setStudentServiceImpl(MemberServiceImpl studentServiceImpl) {
        this.studentServiceImpl = studentServiceImpl;
    }
}