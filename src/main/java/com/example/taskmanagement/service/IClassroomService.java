package com.example.taskmanagement.service;

import com.example.taskmanagement.model.Classroom;

import java.util.List;

public interface IClassroomService {

    Integer createClassroom(Classroom classroom);
    Integer addStudentToClassroom(Long classId, Long studentId);
    Integer deleteAllClassrooms();
    Integer deleteClassroom(Long classId);
    Integer updateClassroom(Classroom classroom);
    List<Classroom> getAllClassrooms();
    List<Classroom> getAllClassroomOfLecturer(Long lecturerId);
    Classroom getClassroom(Long classId);
    Integer countClassroom();
}
