package com.example.taskmanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.taskmanagement.dao.ClassroomStudentMapper;
import com.example.taskmanagement.dao.StudentInfoMapper;
import com.example.taskmanagement.dto.StudentInfo;
import com.example.taskmanagement.po.ClassroomStudentPO;
import com.example.taskmanagement.po.StudentInfoPO;
import com.example.taskmanagement.service.IStudentService;
import com.example.taskmanagement.service.exception.ClassroomNotFoundException;
import com.example.taskmanagement.service.exception.StudentAlreadyInClassroomException;
import com.example.taskmanagement.service.exception.StudentNotInClassroomException;
import com.example.taskmanagement.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements IStudentService {

    private ClassroomStudentMapper classroomStudentMapper;
    private StudentInfoMapper studentInfoMapper;

    @Override
    @Transactional
    public List<StudentInfo> getStudentsJoinedOfClassroom(Long classroomId) {
        QueryWrapper<ClassroomStudentPO> classroomStudentPOQueryWrapper = new QueryWrapper<>();

        classroomStudentPOQueryWrapper.eq("classroom_id", classroomId);
        if (!classroomStudentMapper.exists(classroomStudentPOQueryWrapper))
            throw new ClassroomNotFoundException();

        classroomStudentPOQueryWrapper.clear();

        classroomStudentPOQueryWrapper.select("student_id").eq("classroom_id", classroomId).eq("joined", true);
        List<ClassroomStudentPO> classroomStudentPOS = classroomStudentMapper.selectList(classroomStudentPOQueryWrapper);

        List<Long> studentIds = new ArrayList<>();
        for (ClassroomStudentPO classroomStudentPO: classroomStudentPOS) {
            studentIds.add(classroomStudentPO.getStudentId());
        }
        List<StudentInfoPO> studentInfoPOS = studentInfoMapper.selectBatchIds(studentIds);

        List<StudentInfo> studentInfos = new ArrayList<>();
        for (StudentInfoPO studentInfoPO: studentInfoPOS) {
            studentInfos.add(new StudentInfo(
                    studentInfoPO.getStudentId(),
                    studentInfoPO.getName(),
                    studentInfoPO.getAvatar(),
                    true
            ));
        }
        return studentInfos;
    }

    @Override
    @Transactional
    public List<StudentInfo> getStudentsToBeJoinedOfClassroom(Long classroomId) {
        QueryWrapper<ClassroomStudentPO> classroomStudentPOQueryWrapper = new QueryWrapper<>();

        classroomStudentPOQueryWrapper.eq("classroom_id", classroomId);
        if (!classroomStudentMapper.exists(classroomStudentPOQueryWrapper))
            throw new ClassroomNotFoundException();

        classroomStudentPOQueryWrapper.clear();

        classroomStudentPOQueryWrapper.select("student_id").eq("classroom_id", classroomId).eq("joined", false);
        List<ClassroomStudentPO> classroomStudentPOS = classroomStudentMapper.selectList(classroomStudentPOQueryWrapper);

        List<Long> studentIds = new ArrayList<>();
        for (ClassroomStudentPO classroomStudentPO: classroomStudentPOS) {
            studentIds.add(classroomStudentPO.getStudentId());
        }
        List<StudentInfoPO> studentInfoPOS = studentInfoMapper.selectBatchIds(studentIds);

        List<StudentInfo> studentInfos = new ArrayList<>();
        for (StudentInfoPO studentInfoPO: studentInfoPOS) {
            studentInfos.add(new StudentInfo(
                    studentInfoPO.getStudentId(),
                    studentInfoPO.getName(),
                    studentInfoPO.getAvatar(),
                    false
            ));
        }
        return studentInfos;
    }

    @Override
    @Transactional
    public void deleteStudentFromClassroom(Long classroomId, Long studentId) {
        QueryWrapper<ClassroomStudentPO> classroomStudentPOQueryWrapper = new QueryWrapper<>();

        classroomStudentPOQueryWrapper.eq("classroom_id", classroomId);
        if (!classroomStudentMapper.exists(classroomStudentPOQueryWrapper))
            throw new ClassroomNotFoundException();

        classroomStudentPOQueryWrapper.clear();

        classroomStudentPOQueryWrapper.eq("student_id", studentId);
        if (!classroomStudentMapper.exists(classroomStudentPOQueryWrapper))
            throw new UserNotFoundException();

        classroomStudentPOQueryWrapper.clear();

        classroomStudentPOQueryWrapper.eq("classroom_id", classroomId).eq("student_id", studentId).eq("joined", true);
        if (!classroomStudentMapper.exists(classroomStudentPOQueryWrapper))
            throw new StudentNotInClassroomException();

        classroomStudentPOQueryWrapper.eq("classroom_id", classroomId).eq("student_id", studentId);
        classroomStudentMapper.delete(classroomStudentPOQueryWrapper);
    }

    @Override
    public void acceptStudent(Long classroomId, Long studentId) {
        QueryWrapper<ClassroomStudentPO> classroomStudentPOQueryWrapper = new QueryWrapper<>();

        classroomStudentPOQueryWrapper.eq("classroom_id", classroomId);
        if (!classroomStudentMapper.exists(classroomStudentPOQueryWrapper))
            throw new ClassroomNotFoundException();

        classroomStudentPOQueryWrapper.clear();

        classroomStudentPOQueryWrapper.eq("student_id", studentId);
        if (!classroomStudentMapper.exists(classroomStudentPOQueryWrapper))
            throw new UserNotFoundException();

        classroomStudentPOQueryWrapper.clear();

        classroomStudentPOQueryWrapper.eq("classroom_id", classroomId).eq("student_id", studentId).eq("joined", true);
        if (classroomStudentMapper.exists(classroomStudentPOQueryWrapper))
            throw new StudentAlreadyInClassroomException();

        classroomStudentPOQueryWrapper.clear();

        classroomStudentPOQueryWrapper.eq("classroom_id", classroomId).eq("student_id", studentId);
        ClassroomStudentPO classroomStudentPO = new ClassroomStudentPO(classroomId, studentId, true);
        classroomStudentMapper.update(classroomStudentPO, classroomStudentPOQueryWrapper);
    }

    @Autowired
    public void setClassroomStudentMapper(ClassroomStudentMapper classroomStudentMapper) {
        this.classroomStudentMapper = classroomStudentMapper;
    }

    @Autowired
    public void setStudentInfoMapper(StudentInfoMapper studentInfoMapper) {
        this.studentInfoMapper = studentInfoMapper;
    }
}
