package com.example.taskmanagement.controller.v1;

import com.example.taskmanagement.dto.*;
import com.example.taskmanagement.service.impl.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
public class TaskController {

    private TaskServiceImpl taskServiceImpl;

    /**
     * 教师发布新作业
     * @param classroomId
     * @param newTaskParams
     * @param token
     * @return
     */
    @PostMapping("/classroom/{classroomId}/task")
    @PreAuthorize("@preAuthorizeHelper.isClassroomLecturer(#classroomId, #token)")
    public Result newTask(@PathVariable Long classroomId,
                          @RequestBody NewTaskParams newTaskParams,
                          @RequestHeader("Authorization") String token) {
        taskServiceImpl.newTask(newTaskParams);
        return Result.onlyMessage("Create new task successfully");
    }

    /**
     * 学生提交作业内容
     * @param classroomId
     * @param userId
     * @param studentAnswerParams
     * @param token
     * @return
     */
    @PostMapping("/classroom/{classroomId}/student/{userId}/task")
    @PreAuthorize("@preAuthorizeHelper.isClassroomStudent(#classroomId, #token) and " +
                  "@preAuthorizeHelper.isUserSelf(#userId, #token)")
    public Result submitStudentTask(@PathVariable Long classroomId,
                                    @PathVariable Long userId,
                                    @RequestBody StudentAnswerParams studentAnswerParams,
                                    @RequestHeader("Authorization") String token) {
        taskServiceImpl.submitStudentTask(studentAnswerParams);
        return Result.onlyMessage("Submit student task successfully");
    }

    /**
     * 教师批阅学生作业
     * @param classroomId
     * @param userId
     * @param taskId
     * @param remarkParams
     * @param token
     * @return
     */
    @PutMapping("/classrooms/{classroomId}/students/{userId}/tasks/{taskId}/remark")
    @PreAuthorize("@preAuthorizeHelper.isClassroomLecturer(#classroomId, #token)")
    public Result remarkStudentTask(@PathVariable Long classroomId,
                                    @PathVariable Long userId,
                                    @PathVariable Long taskId,
                                    @RequestBody RemarkParams remarkParams,
                                    @RequestHeader("Authorization") String token) {
        taskServiceImpl.remarkStudentTask(remarkParams);
        return Result.onlyMessage("Remark student task successfully");
    }

    /**
     * 班级成员获取该班所有作业
     * @param classroomId
     * @param token
     * @return
     */
    @GetMapping("/classroom/{classroomId}/tasks")
    @PreAuthorize("@preAuthorizeHelper.isClassroomMember(#classroomId, #token)")
    public Result getTasksOfClassroom(@PathVariable Long classroomId,
                                      @RequestHeader("Authorization") String token) {
        ClassroomTaskInfo classroomTaskInfo = taskServiceImpl.getTasksOfClassroom(classroomId);
        return new Result<>("Got all tasks of the classroom successfully", classroomTaskInfo);
    }

    /**
     * 获取所有学生该作业完成情况
     * @param classroomId
     * @param taskId
     * @param token
     * @return
     */
    @GetMapping("/classroom/{classroomId}/task/{taskId}/students")
    @PreAuthorize("@preAuthorizeHelper.isClassroomMember(#classroomId, #token)")
    public Result getAllStudentProgressOfTask(@PathVariable Long classroomId,
                                       @PathVariable Long taskId,
                                       @RequestHeader("Authorization") String token) {
        StudentTaskProgressInfo studentTaskProgressInfo =  taskServiceImpl.getAllStudentProgressOfTask(taskId);
        return new Result("Got all student progress of the task successfully", studentTaskProgressInfo);
    }

    /**
     * 获取学生作业内容
     * @param classroomId
     * @param studentId
     * @param taskId
     * @param token
     * @return
     */
    @GetMapping("/classroom/{classroomId}/student/{studentId}/task/{taskId}")
    @PreAuthorize("@preAuthorizeHelper.isClassroomLecturer(#classroomId, #token) or " +
                  "(@preAuthorizeHelper.isClassroomStudent(#classroomId, #token) and @preAuthorizeHelper.isUserSelf(#studentId, #token))")
    public Result getStudentTask(@PathVariable Long classroomId,
                                 @PathVariable Long studentId,
                                 @PathVariable Long taskId,
                                 @RequestHeader("Authorization") String token) {
        StudentTaskInfo studentTaskInfo = taskServiceImpl.getStudentTask(taskId, studentId);
        return new Result("Got the student task info successfully", studentTaskInfo);
    }

    @DeleteMapping("/classroom/{classroomId}/task/{taskId}")
    @PreAuthorize("@preAuthorizeHelper.isClassroomLecturer(#classroomId, #token)")
    public Result deleteTask(@PathVariable Long classroomId,
                             @PathVariable Long taskId,
                             @RequestHeader("Authorization") String token) {
        taskServiceImpl.deleteTask(taskId);
        return Result.onlyMessage("Deleted the task successfully");
    }

    @Autowired
    public void setTaskServiceImpl(TaskServiceImpl taskServiceImpl) {
        this.taskServiceImpl = taskServiceImpl;
    }
}
