package com.example.taskmanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.taskmanagement.dao.ClassroomMapper;
import com.example.taskmanagement.dao.ClassroomStudentMapper;
import com.example.taskmanagement.dto.ClassroomInfo;
import com.example.taskmanagement.dto.ClassroomParams;
import com.example.taskmanagement.po.ClassroomPO;
import com.example.taskmanagement.po.ClassroomStudentPO;
import com.example.taskmanagement.service.IClassroomService;
import com.example.taskmanagement.service.exception.RepeatedJoinClassroomException;
import com.example.taskmanagement.service.exception.StudentAlreadyInClassroomException;
import com.example.taskmanagement.utils.ShareCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClassroomServiceImpl implements IClassroomService {

    private ClassroomMapper classroomMapper;
    private ClassroomStudentMapper classroomStudentMapper;

    @Override
    @Transactional
    public void newClassroom(ClassroomParams classroomParams) {
        Long roomCount = classroomMapper.selectCount(null);
        ClassroomPO classroomPO = new ClassroomPO(
                null,
                classroomParams.getLecturerId(),
                classroomParams.getClassName(),
                ShareCodeUtil.toSerialCode(roomCount)
        );
        classroomMapper.insert(classroomPO);
    }

    @Override
    @Transactional
    public List<ClassroomInfo> getClassroomsOfLecturer(Long lecturerId) {
        QueryWrapper<ClassroomPO> classroomPOQueryWrapper = new QueryWrapper<>();
        classroomPOQueryWrapper.eq("lecturer_id", lecturerId);
        List<ClassroomPO> classroomPOS = classroomMapper.selectList(classroomPOQueryWrapper);

        List<ClassroomInfo> classroomInfos = new ArrayList<>();
        QueryWrapper<ClassroomStudentPO> classroomStudentPOQueryWrapper = new QueryWrapper<>();
        for (ClassroomPO classroomPO: classroomPOS) {
            classroomStudentPOQueryWrapper.clear();
            classroomStudentPOQueryWrapper.eq("classroom_id", classroomPO.getClassroomId()).eq("joined", true);
            Integer studentCount = classroomStudentMapper.selectCount(classroomStudentPOQueryWrapper).intValue();
            classroomInfos.add(new ClassroomInfo(
                    classroomPO.getClassroomId(),
                    classroomPO.getLecturerId(),
                    classroomPO.getClassName(),
                    classroomPO.getClassCode(),
                    studentCount
            ));
        }
        return classroomInfos;
    }

    @Override
    @Transactional
    public List<ClassroomInfo> getClassroomsOfStudent(Long studentId) {
        QueryWrapper<ClassroomStudentPO> classroomStudentPOQueryWrapper = new QueryWrapper<>();
        classroomStudentPOQueryWrapper.eq("student_id", studentId).eq("joined", true);
        List<ClassroomStudentPO> classroomStudentPOS = classroomStudentMapper.selectList(classroomStudentPOQueryWrapper);

        List<Long> classroomIds = new ArrayList<>();
        for (ClassroomStudentPO classroomStudentPO: classroomStudentPOS) {
            classroomIds.add(classroomStudentPO.getClassroomId());
        }

        List<ClassroomPO> classroomPOS = classroomMapper.selectBatchIds(classroomIds);
        List<ClassroomInfo> classroomInfos = new ArrayList<>();
        classroomPOS.forEach(classroomPO -> {
            Integer studentCount = classroomStudentMapper.selectCount(classroomStudentPOQueryWrapper).intValue();
            classroomInfos.add(new ClassroomInfo(
                    classroomPO.getClassroomId(),
                    classroomPO.getLecturerId(),
                    classroomPO.getClassName(),
                    classroomPO.getClassCode(),
                    studentCount
            ));
        });
        return classroomInfos;
    }

    @Override
    public void deleteClassroomOfLecturer(Long classroomId) {
        QueryWrapper<ClassroomPO> classroomPOQueryWrapper = new QueryWrapper<>();
        classroomPOQueryWrapper.eq("classroom_id", classroomId);
        classroomMapper.delete(classroomPOQueryWrapper);
    }

    @Override
    public void quitClassroom(Long classId, Long studentId) {
        QueryWrapper<ClassroomStudentPO> classroomStudentPOQueryWrapper = new QueryWrapper<>();
        classroomStudentPOQueryWrapper.eq("classroom_id", classId).eq("student_id", studentId);
        classroomStudentMapper.delete(classroomStudentPOQueryWrapper);
    }

    @Override
    @Transactional
    public void joinClassroom(Long classId, Long studentId) {
        QueryWrapper<ClassroomStudentPO> classroomStudentPOQueryWrapper = new QueryWrapper<>();
        classroomStudentPOQueryWrapper.eq("classroom_id", classId).eq("student_id", studentId);
        if (classroomStudentMapper.exists(classroomStudentPOQueryWrapper)) {
            ClassroomStudentPO classroomStudentPO = classroomStudentMapper.selectOne(classroomStudentPOQueryWrapper);
            if (classroomStudentPO.isJoined())
                throw new StudentAlreadyInClassroomException();
            else
                throw new RepeatedJoinClassroomException();
        }
        ClassroomStudentPO classroomStudentPO = new ClassroomStudentPO(
                classId,
                studentId,
                false
        );
        classroomStudentMapper.insert(classroomStudentPO);
    }

    @Autowired
    public void setClassroomMapper(ClassroomMapper classroomMapper) {
        this.classroomMapper = classroomMapper;
    }

    @Autowired
    public void setClassroomStudentMapper(ClassroomStudentMapper classroomStudentMapper) {
        this.classroomStudentMapper = classroomStudentMapper;
    }
}
