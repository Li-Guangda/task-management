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

    @PostMapping("/classroom/{classroomId}/task")
    @PreAuthorize("hasRole('ROLE_LECTURER') and " +
                  "@preAuthorizeHelper.isClassroomLecturer(#classroomId, #token)")
    public Result newTask(@PathVariable Long classroomId,
                          @RequestBody NewTaskParams newTaskParams,
                          @RequestHeader("Authrization") String token) {
        taskServiceImpl.newTask(newTaskParams);
        return Result.onlyMessage("Create new task successfully");
    }

    @PostMapping("/classroom/{classroomId}/student/{userId}/{studentId}/task")
    @PreAuthorize("hasRole('ROLE_STUDENT') and " +
                  "@preAuthorizeHelper.isClassroomStudent(#classroomId, #token) and " +
                  "@preAuthorizeHelper.isUserSelf(#userId, #token)")
    public Result submitStudentTask(@PathVariable Long classroomId,
                                    @PathVariable Long userId,
                                    @RequestBody StudentAnswerParams studentAnswerParams,
                                    @RequestHeader("Authorization") String token) {
        taskServiceImpl.submitStudentTask(studentAnswerParams);
        return Result.onlyMessage("Submit student task successfully");
    }

    @PutMapping("/classroom/{classroomId}/student/{userId}/task/{taskId}/remark")
    @PreAuthorize("hasRole('ROLE_LECTURER') and " +
                  "@preAuthorizeHelper.isClassroomLecturer(#classroomId, #token)")
    public Result remarkStudentTask(@PathVariable Long classroomId,
                                    @PathVariable Long userId,
                                    @PathVariable Long taskId,
                                    @RequestBody RemarkParams remarkParams,
                                    @RequestHeader("Authorization") String token) {
        taskServiceImpl.remarkStudentTask(remarkParams);
        return Result.onlyMessage("Remark student task successfully");
    }

    @GetMapping("/classroom/{classroomId}/tasks")
    @PreAuthorize("@preAuthorizeHelper.isClassroomMember(#classroomId, #token)")
    public Result getTasksOfClassroom(@PathVariable Long classroomId,
                                      @RequestHeader("Authorization") String token) {
        ClassroomTaskInfo classroomTaskInfo = taskServiceImpl.getTasksOfClassroom(classroomId);
        return new Result<>("Got all tasks of the classroom successfully", classroomTaskInfo);
    }

    @GetMapping("/classroom/{classroomId}/task/{taskId}/students")
    @PreAuthorize("@preAuthorizeHelper.isClassroomMember(#classroomId, #token)")
    public Result getAllStudentProgressOfTask(@PathVariable Long classroomId,
                                       @PathVariable Long taskId,
                                       @RequestHeader("Authorization") String token) {
        StudentTaskProgressInfo studentTaskProgressInfo =  taskServiceImpl.getAllStudentProgressOfTask(taskId);
        return new Result("Got all student progress of the task successfully", studentTaskProgressInfo);
    }

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

    @GetMapping("/classroom/{classroomId}/task/{taskId}")
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
