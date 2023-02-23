package com.example.taskmanagement.controller.v1;

import com.example.taskmanagement.controller.response.Result;
import com.example.taskmanagement.model.StudentTask;
import com.example.taskmanagement.model.Task;
import com.example.taskmanagement.service.impl.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TaskController {

    private TaskService taskService;

    @PostMapping("/class/{classId}/task")
    public ResponseEntity<?> addTask(@PathVariable Long classId, @RequestBody @Validated Task task) {
        Integer res = taskService.addTask(task);
        if (res != 1) {
            return ResponseEntity.status(400).body(new Result<>("Cannot add task", null));
        }
        return ResponseEntity.ok(new Result<>("Add task successfully", null));
    }

    @PostMapping("/class/{classId}/student/{studentId}/task/{taskId}")
    public ResponseEntity<?> addStudentTask(@PathVariable Long classId, @PathVariable Long studentId, @RequestBody StudentTask studentTask) {
        Integer res = taskService.addStudentTask(studentTask);
        if (res != 1) {
            return ResponseEntity.status(400).body(new Result<>("Cannot add student task", null));
        }
        return ResponseEntity.ok(new Result<>("Add student task successfully", null));
    }

    @DeleteMapping("/class/{classId}/tasks")
    public ResponseEntity<?> deleteAllTasksOfClass(@PathVariable Long classId) {
        taskService.deleteAllTasksOfClass(classId);
        return ResponseEntity.ok(new Result<>("Delete all tasks successfully", null));
    }

    @DeleteMapping("/class/{classId}/task/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long classId, @PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.ok(new Result<>("Delete task successfully", null));
    }

    @DeleteMapping("/class/{classId}/student/{studentId}/task/{taskId}")
    public ResponseEntity<?> deleteStudentTask(@PathVariable Long classId, @PathVariable Long studentId, @PathVariable Long taskId) {
        taskService.deleteStudentTask(studentId, taskId);
        return ResponseEntity.ok(new Result<>("Delete student task successfully", null));
    }

    @PutMapping("/class/{classId}/task/{taskId}")
    public ResponseEntity<?> updateTask(@PathVariable Long classId, @PathVariable Long taskId, @RequestBody @Validated  Task task) {
        Integer res = taskService.updateTask(task);
        if (res != 1) {
            return ResponseEntity.status(400).body(new Result<>("Updated task failed", null));
        }
        return ResponseEntity.ok(new Result<>("Updated task successfully", null));
    }

    @PutMapping("/class/{classId}/student/{studentId}/task/{taskId}")
    public ResponseEntity<?> updateStudentTask(@PathVariable Long classId, @PathVariable Long studentId, @PathVariable Long taskId, @RequestBody StudentTask studentTask) {
        Integer res = taskService.updateStudentTask(studentTask);
        if (res != 1) {
            return ResponseEntity.status(400).body(new Result<>("Updated student task failed", null));
        }
        return ResponseEntity.ok(new Result<>("Updated student task successfully", null));
    }

    @GetMapping("/class/{classId}/tasks")
    public ResponseEntity<?> getTasksOfClass(@PathVariable Long classId) {
        List<Task> tasks = taskService.getAllTaskOfClass(classId);
        if (tasks == null) {
            return ResponseEntity.status(400).body(new Result<>("Got all tasks failed", null));
        }
        return ResponseEntity.ok(new Result<>("Got all tasks successfully", tasks));
    }

    @GetMapping("/class/{classId}/task/{taskId}")
    public ResponseEntity<?> getTaskOfClass(@PathVariable Long classId, @PathVariable Long taskId) {
        Task task = taskService.getTask(taskId);
        if (task == null) {
            return ResponseEntity.status(400).body(new Result<>("Got task failed", null));
        }
        return ResponseEntity.ok(new Result<>("Got task successfully", task));
    }

    @GetMapping("/class/{classId}/student/{studentId}/task/{taskId}")
    public ResponseEntity<?> getStudentTaskOfClass(@PathVariable Long classId, @PathVariable Long studentId, @PathVariable Long taskId) {
        StudentTask studentTask = taskService.getStudentTask(studentId, taskId);
        if (studentTask == null) {
            return ResponseEntity.status(400).body(new Result<>("Got student task failed", null));
        }
        return ResponseEntity.ok(new Result<>("Got student task successfully", studentTask));
    }

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }
}
