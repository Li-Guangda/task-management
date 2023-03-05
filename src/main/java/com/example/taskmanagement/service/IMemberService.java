package com.example.taskmanagement.service;

import com.example.taskmanagement.dto.StudentInfos;


public interface IMemberService {

    StudentInfos getStudentsJoinedOfClassroom(Long classroomId);
    StudentInfos getStudentsToBeJoinedOfClassroom(Long classroomId);
    void deleteStudentFromClassroom(Long classroomId, Long studentId);
    void acceptStudent(Long classroomId, Long studentId);
}
