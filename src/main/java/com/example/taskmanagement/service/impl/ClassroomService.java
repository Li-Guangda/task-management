package com.example.taskmanagement.service.impl;

import com.example.taskmanagement.mapper.ClassroomMapper;
import com.example.taskmanagement.model.Classroom;
import com.example.taskmanagement.service.IClassroomService;
import com.example.taskmanagement.utils.ShareCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassroomService implements IClassroomService {

    private ClassroomMapper classroomMapper;

    @Override
    public Integer createClassroom(Classroom classroom) {
        Integer roomCount = classroomMapper.getClassroomCount();
        String code = ShareCodeUtil.toSerialCode(roomCount);
        classroom.setClassCode(code);
        return classroomMapper.addClassroom(
            classroom.getLecturerId(),
            classroom.getClassName(),
            classroom.getClassCode()
        );
    }

    @Override
    public Integer addStudentToClassroom(Long classId, Long studentId) {
        return classroomMapper.addStudentToClass(classId, studentId);
    }

    @Override
    public Integer deleteAllClassrooms() {
        return classroomMapper.deleteAllClassrooms();
    }

    @Override
    public Integer deleteClassroom(Long classId) {
        return classroomMapper.deleteClassroomByClassId(classId);
    }

    @Override
    public Integer updateClassroom(Classroom classroom) {
        return classroomMapper.updateClassroomByClassId(
            classroom.getClassId(),
            classroom.getClassName()
        );
    }

    @Override
    public List<Classroom> getAllClassrooms() {
        return classroomMapper.getAllClassrooms();
    }

    @Override
    public List<Classroom> getAllClassroomOfLecturer(Long lecturerId) {
        return classroomMapper.getAllClassroomsByLecturerId(lecturerId);
    }

    @Override
    public Classroom getClassroom(Long classId) {
        return classroomMapper.getClassroomByClassId(classId);
    }

    @Override
    public Integer countClassroom() {
        return classroomMapper.getClassroomCount();
    }

    @Autowired
    public void setClassroomMapper(ClassroomMapper classroomMapper) {
        this.classroomMapper = classroomMapper;
    }
}
