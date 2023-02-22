package com.example.taskmanagement.service.impl;

import com.example.taskmanagement.mapper.StudentTaskMapper;
import com.example.taskmanagement.mapper.TaskMapper;
import com.example.taskmanagement.model.StudentTask;
import com.example.taskmanagement.model.Task;
import com.example.taskmanagement.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService implements ITaskService {

    private TaskMapper taskMapper;
    private StudentTaskMapper studentTaskMapper;

    @Override
    public Integer addTask(Task task) {
        return taskMapper.addTask(
            task.getClassId(),
                task.getTaskTitle(),
                task.getTaskDesc(),
                task.getDateEnd()
        );
    }

    @Override
    public Integer addStudentTask(StudentTask studentTask) {
        return studentTaskMapper.addStudentTask(
            studentTask.getStudentId(),
            studentTask.getTaskId(),
            studentTask.getScore(),
            studentTask.getRemark(),
            studentTask.getAttachment(),
            studentTask.getContent()
        );
    }

    @Override
    public Integer deleteAllTasksOfClass(Long classId) {
        return taskMapper.deleteAllTasksOfClass(classId);
    }

    @Override
    public Integer deleteTask(Long taskId) {
        return taskMapper.deleteTaskById(taskId);
    }

    @Override
    public Integer deleteStudentTask(Long studentId, Long taskId) {
        return studentTaskMapper.deleteStudentTaskByStudentIdAndTaskId(studentId, taskId);
    }

    @Override
    public Integer updateTask(Task task) {
        return taskMapper.updateTaskById(
            task.getTaskId(),
            task.getClassId(),
            task.getTaskTitle(),
            task.getTaskDesc(),
            task.getDateStart(),
            task.getDateEnd()
        );
    }

    @Override
    public Integer updateStudentTask(StudentTask studentTask) {
        return studentTaskMapper.updateStudentTaskByStudentIdAndTaskId(
            studentTask.getStudentId(),
            studentTask.getTaskId(),
            studentTask.getScore(),
            studentTask.getRemark(),
            studentTask.getAttachment(),
            studentTask.getContent()
        );
    }

    @Override
    public List<Task> getAllTaskOfClass(Long classId) {
        return taskMapper.getAllTasksOfClass(classId);
    }

    @Override
    public Task getTask(Long taskId) {
        return taskMapper.getTaskById(taskId);
    }

    @Override
    public List<StudentTask> getAllStudentTaskOfClass(Long classId, Long taskId) {
        return studentTaskMapper.getStuentTasksByClassIdAndTaskId(classId, taskId);
    }

    @Override
    public StudentTask getStudentTask(Long studentId, Long taskId) {
        return studentTaskMapper.getStudentTask(studentId, taskId);
    }

    @Autowired
    public void setTaskMapper(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    @Autowired
    public void setStudentTaskMapper(StudentTaskMapper studentTaskMapper) {
        this.studentTaskMapper = studentTaskMapper;
    }
}
