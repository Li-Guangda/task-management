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
    @PostMapping("/classrooms/{classroomId}/task")
    @PreAuthorize("@preAuthorizeHelper.isClassroomLecturer(#classroomId, #token)")
    public Result newTask(@PathVariable Long classroomId,
                          @RequestBody NewTaskParams newTaskParams,
                          @RequestHeader("Authorization") String token) {
        taskServiceImpl.newTask(newTaskParams);
        return Result.onlyMessage("Successfully created a new task");
    }

    /**
     * 学生提交作答内容
     * @param classroomId
     * @param userId
     * @param studentAnswerParams
     * @param token
     * @return
     */
    @PostMapping("/classrooms/{classroomId}/students/{userId}/answer")
    @PreAuthorize("@preAuthorizeHelper.isClassroomStudent(#classroomId, #token) and " +
                  "@preAuthorizeHelper.isUserSelf(#userId, #token)")
    public Result submitStudentAnswer(@PathVariable Long classroomId,
                                      @PathVariable Long userId,
                                      @RequestBody StudentAnswerParams studentAnswerParams,
                                      @RequestHeader("Authorization") String token) {
        taskServiceImpl.submitStudentAnswer(studentAnswerParams);
        return Result.onlyMessage("Successfully submitted the student task");
    }

    /**
     * 教师批阅学生作业
     * @param classroomId
     * @param remarkParams
     * @param token
     * @return
     */
    @PutMapping("/classrooms/{classroomId}/tasks/{taskId}/remark")
    @PreAuthorize("@preAuthorizeHelper.isClassroomLecturer(#classroomId, #token)")
    public Result remarkStudentTask(@PathVariable Long classroomId,
                                    @RequestBody RemarkParams remarkParams,
                                    @RequestHeader("Authorization") String token) {
        taskServiceImpl.remarkStudentTask(remarkParams);
        return Result.onlyMessage("Successfully remarked the student task");
    }

    /**
     * 班级成员获取该班所有作业
     * @param classroomId
     * @param token
     * @return
     */
    @GetMapping("/classrooms/{classroomId}/tasks")
    @PreAuthorize("@preAuthorizeHelper.isClassroomMember(#classroomId, #token)")
    public Result getTasksOfClassroom(@PathVariable Long classroomId,
                                      @RequestHeader("Authorization") String token) {
        ClassroomTaskInfo classroomTaskInfo = taskServiceImpl.getTasksOfClassroom(classroomId);
        return new Result<>("Successfully got all tasks of the classroom", classroomTaskInfo);
    }

    /**
     * 获取所有学生该作业状态
     * @param classroomId
     * @param taskId
     * @param token
     * @return
     */
    @GetMapping("/classrooms/{classroomId}/tasks/{taskId}/students")
    @PreAuthorize("@preAuthorizeHelper.isClassroomMember(#classroomId, #token)")
    public Result getAllStudentProgressOfTask(@PathVariable Long classroomId,
                                              @PathVariable Long taskId,
                                              @RequestHeader("Authorization") String token) {
        StudentTaskProgressInfos studentTaskProgressInfos =  taskServiceImpl.getStudentsOfTask(taskId);
        return new Result("Successfully got all student progress of the task", studentTaskProgressInfos);
    }

    /**
     * 获取学生作答内容
     * @param classroomId
     * @param studentId
     * @param taskId
     * @param token
     * @return
     */
    @GetMapping("/classrooms/{classroomId}/students/{studentId}/tasks/{taskId}")
    @PreAuthorize("@preAuthorizeHelper.isClassroomLecturer(#classroomId, #token) or " +
                  "(@preAuthorizeHelper.isClassroomStudent(#classroomId, #token) and @preAuthorizeHelper.isUserSelf(#studentId, #token))")
    public Result getStudentTask(@PathVariable Long classroomId,
                                 @PathVariable Long studentId,
                                 @PathVariable Long taskId,
                                 @RequestHeader("Authorization") String token) {
        StudentTaskInfo studentTaskInfo = taskServiceImpl.getStudentAnswer(taskId, studentId);
        return new Result("Successfully got the student task info", studentTaskInfo);
    }

    /**
     * 教师删除作业
     * @param classroomId
     * @param taskId
     * @param token
     * @return
     */
    @DeleteMapping("/classrooms/{classroomId}/tasks/{taskId}")
    @PreAuthorize("@preAuthorizeHelper.isClassroomLecturer(#classroomId, #token)")
    public Result deleteTask(@PathVariable Long classroomId,
                             @PathVariable Long taskId,
                             @RequestHeader("Authorization") String token) {
        taskServiceImpl.deleteTask(taskId);
        return Result.onlyMessage("Successfully deleted the task");
    }

    /**
     * 班级成员获取作业内容
     * @param classroomId
     * @param taskId
     * @param token
     * @return
     */
    @GetMapping("/classrooms/{classroomId}/tasks/{taskId}")
    @PreAuthorize("@preAuthorizeHelper.isClassroomMember(#classroomId, #token)")
    public Result getTaskContent(@PathVariable Long classroomId,
                                 @PathVariable Long taskId,
                                 @RequestHeader("Authorization") String token) {
        TaskContent taskContent = taskServiceImpl.getTaskContent(taskId);
        return new Result<>("Successfully got the content of the task", taskContent);
    }

    @GetMapping("/classrooms/{classroomId}/students/{studentId}/tasks/{taskId}/answers")
    @PreAuthorize("@preAuthorizeHelper.isClassroomMember(#classroomId, #token)")
    public Result getTaskAnswer(@PathVariable Long classroomId,
                                 @PathVariable Long taskId,
                                 @PathVariable Long studentId,
                                 @RequestHeader("Authorization") String token) {
        QuestionAnswer questionAnswer = taskServiceImpl.getTaskAnswers(taskId, studentId);
        return new Result<>("Successfully got the answers of the task", questionAnswer);
    }

    @Autowired
    public void setTaskServiceImpl(TaskServiceImpl taskServiceImpl) {
        this.taskServiceImpl = taskServiceImpl;
    }
}
