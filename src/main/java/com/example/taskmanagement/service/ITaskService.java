package com.example.taskmanagement.service;

import com.example.taskmanagement.model.StudentTask;
import com.example.taskmanagement.model.Task;

import java.util.List;

public interface ITaskService {

    Integer addTask(Task task);
    Integer addStudentTask(StudentTask studentTask);
    Integer deleteAllTasksOfClass(Long classId);
    Integer deleteTask(Long taskId);
    Integer deleteStudentTask(Long studentId, Long taskId);
    Integer updateTask(Task task);
    Integer updateStudentTask(StudentTask studentTask);
    List<Task> getAllTaskOfClass(Long classId);
    Task getTask(Long taskId);
    List<StudentTask> getAllStudentTaskOfClass(Long studentId, Long taskId);
    StudentTask getStudentTask(Long studentId, Long taskId);
}
