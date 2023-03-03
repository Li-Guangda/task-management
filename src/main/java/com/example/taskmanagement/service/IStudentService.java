package com.example.taskmanagement.service;

import com.example.taskmanagement.dto.StudentInfo;

import java.util.List;

public interface IStudentService {

    List<StudentInfo> getStudentsJoinedOfClassroom(Long classroomId);
    List<StudentInfo> getStudentsToBeJoinedOfClassroom(Long classroomId);
    void deleteStudentFromClassroom(Long classroomId, Long studentId);
    void acceptStudent(Long classroomId, Long studentId);
}
