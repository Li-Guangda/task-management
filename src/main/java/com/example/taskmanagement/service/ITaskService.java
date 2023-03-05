package com.example.taskmanagement.service;


import com.example.taskmanagement.dto.*;

import java.text.DecimalFormat;

public interface ITaskService {

    void newTask(NewTaskParams newTaskParams);
    void submitStudentAnswer(StudentAnswerParams studentAnswerParams);
    void remarkStudentTask(RemarkParams remarkParams);
    ClassroomTaskInfo getTasksOfClassroom(Long classId);
    StudentTaskProgressInfos getStudentsOfTask(Long tasId);
    TaskContent getTaskContent(Long taskId);
    QuestionAnswer getTaskAnswers(Long taskId, Long studentId);
    StudentTaskInfo getStudentAnswer(Long taskId, Long studentId);
    String getChoiceQuestionAccuracyOfClassroom(Long choiceQuestionId);
    String getRightAnswerOfChoiceQuestion(Long choiceQuestionId);
    String getStudentAnswerOfChoiceQuestion(Long choiceQuestionId, Long studentId);
    void deleteTask(Long taskId);
}