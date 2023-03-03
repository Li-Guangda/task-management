package com.example.taskmanagement.service;


import com.example.taskmanagement.dto.NewTaskParams;
import com.example.taskmanagement.dto.RemarkParams;
import com.example.taskmanagement.dto.StudentAnswerParams;
import com.example.taskmanagement.dto.StudentTaskProgressInfo;
import com.example.taskmanagement.dto.ClassroomTaskInfo;
import com.example.taskmanagement.dto.StudentTaskInfo;

public interface ITaskService {

    void newTask(NewTaskParams requestBody);
    void submitStudentTask(StudentAnswerParams requestBody);
    void remarkStudentTask(RemarkParams requestBody);
    ClassroomTaskInfo getTasksOfClassroom(Long classId);
    StudentTaskProgressInfo getAllStudentProgressOfTask(Long classId);
    StudentTaskInfo getStudentTask(Long classId, Long studentId);
    void deleteTask(Long taskId);
}
