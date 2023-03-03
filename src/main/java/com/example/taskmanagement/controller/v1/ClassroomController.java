package com.example.taskmanagement.controller.v1;

import com.example.taskmanagement.dto.ClassroomInfo;
import com.example.taskmanagement.dto.ClassroomInfos;
import com.example.taskmanagement.dto.ClassroomParams;
import com.example.taskmanagement.dto.Result;
import com.example.taskmanagement.service.exception.RepeatedJoinClassroomException;
import com.example.taskmanagement.service.exception.StudentAlreadyInClassroomException;
import com.example.taskmanagement.service.impl.ClassroomServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ClassroomController {

    private ClassroomServiceImpl classroomServiceImpl;

    /**
     * 创建班级
     * @param classroomParams
     * @return
     */
    @PostMapping("/classroom")
    @PreAuthorize("hasRole('ROLE_LECTURER')")
    public Result newClassroom(@RequestBody ClassroomParams classroomParams) {
        classroomServiceImpl.newClassroom(classroomParams);
        return Result.onlyMessage("Create new classroom successfully");
    }

    /**
     * 获取该教师创建的所有班级
     * @param userId
     * @param token
     * @return
     */
    @GetMapping("/user/lecturer/{userId}/classrooms")
    @PreAuthorize("hasRole('ROLE_LECTURER') and @preAuthorizeHelper.isUserSelf(#userId, #token)")
    public Result getClassroomsOfLecturer(@PathVariable Long userId,
                                          @RequestHeader("Authorization") String token) {
        List<ClassroomInfo> classroomInfos = classroomServiceImpl.getClassroomsOfLecturer(userId);
        return new Result("Got all classroom info of the lecturer successfully", new ClassroomInfos(classroomInfos));
    }

    /**
     * 获取该学生加入的所有班级
     * @param userId
     * @param token
     * @return
     */
    @GetMapping("/user/student/{userId}/classrooms")
    @PreAuthorize("hasRole('ROLE_STUDENT') and @preAuthorizeHelper.isUserSelf(#userId, #token)")
    public Result getClassroomsOfStudent(@PathVariable Long userId,
                                         @RequestHeader("Authorization") String token) {
        List<ClassroomInfo> classroomInfos = classroomServiceImpl.getClassroomsOfStudent(userId);
        return new Result("Got all classroom info that the student joined successfully", classroomInfos);
    }

    /**
     * 教师删除班级
     * @param userId
     * @param classroomId
     * @param token
     * @return
     */
    @DeleteMapping("/user/lecturer/{userId}/classroom/{classroomId}")
    @PreAuthorize("@preAuthorizeHelper.isClassroomLecturer(#classroomId, #token)")
    public Result deleteClassroomOfLecturer(@PathVariable Long userId,
                                            @PathVariable Long classroomId,
                                            @RequestHeader("Authorization") String token) {
        classroomServiceImpl.deleteClassroomOfLecturer(classroomId);
        return Result.onlyMessage("Deleted the classroom successfully");
    }

    /**
     * 学生退出班级
     * @param classroomId
     * @param userId
     * @param token
     * @return
     */
    @DeleteMapping("/classroom/{classroomId}/student/{userId}")
    @PreAuthorize("@preAuthorizeHelper.isClassroomStudent(#classroomId, #token)")
    public Result quitClassroom(@PathVariable Long classroomId,
                                @PathVariable Long userId,
                                @RequestHeader("Authorization") String token) {
        classroomServiceImpl.quitClassroom(classroomId, userId);
        return Result.onlyMessage("Quit the classroom successfully");
    }

    /**
     * 学生请求加入班级
     * @param classroomId
     * @param userId
     * @return
     */
    @PostMapping("/classroom/{classroomId}/student/{userId}")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<Result> joinClassroom(@PathVariable Long classroomId,
                                                @PathVariable Long userId) {
        String message;
        Integer code;
        try {
            classroomServiceImpl.joinClassroom(classroomId, userId);
            code = 200;
            message = "Tried to join the classroom successfully";
        } catch (StudentAlreadyInClassroomException e) {
            code = 400;
            message = "The student is already in the classroom";
        } catch (RepeatedJoinClassroomException e) {
            code = 400;
            message = "You have tried to join the classroom, please wait the lecturer's response";
        }
        return ResponseEntity.status(code).body(Result.onlyMessage(message));
    }

    @Autowired
    public void setClassroomServiceImpl(ClassroomServiceImpl classroomServiceImpl) {
        this.classroomServiceImpl = classroomServiceImpl;
    }
}
