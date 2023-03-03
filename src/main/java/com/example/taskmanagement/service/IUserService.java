package com.example.taskmanagement.service;

import com.example.taskmanagement.dto.LecturerDetailedInfo;
import com.example.taskmanagement.dto.LogInParams;
import com.example.taskmanagement.dto.SignUpParams;
import com.example.taskmanagement.dto.StudentDetailedInfo;

public interface IUserService {
    /**
     * 注册
     * @param signUpParams
     * @return
     */
    void signUp(SignUpParams signUpParams);

    /**
     * 登录
     * @param logInParams
     * @return
     */
    String logIn(LogInParams logInParams);

    /**
     * 获取学生详细信息
     * @param userId
     * @return
     */
    StudentDetailedInfo getStudentInfo(Long userId);

    /**
     * 获取教师详细信息
     * @param userId
     * @return
     */
    LecturerDetailedInfo getLecturerInfo(Long userId);

    /**
     * 更新学生详细信息
     * @param studentDetailedInfo
     */
    void updateStudentInfo(StudentDetailedInfo studentDetailedInfo);

    /**
     * 更新教师详细信息
     * @param lecturerDetailedInfo
     */
    void updateLecturerInfo(LecturerDetailedInfo lecturerDetailedInfo);
}
