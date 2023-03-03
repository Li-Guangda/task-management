package com.example.taskmanagement.service;

import com.example.taskmanagement.dto.ClassroomInfo;
import com.example.taskmanagement.dto.ClassroomParams;

import java.util.List;

public interface IClassroomService {

    /**
     * 创建班级
     * @param classroomParams
     */
    void newClassroom(ClassroomParams classroomParams);

    /**
     * 获取当前教师的所有班级信息
     * @param lecturerId
     * @return
     */
    List<ClassroomInfo> getClassroomsOfLecturer(Long lecturerId);
    List<ClassroomInfo> getClassroomsOfStudent(Long studentId);
    void deleteClassroomOfLecturer(Long lecturerId);
    void quitClassroom(Long classId, Long studentId);
    void joinClassroom(Long classId, Long studentId);
}
