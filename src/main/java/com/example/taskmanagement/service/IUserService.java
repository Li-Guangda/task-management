package com.example.taskmanagement.service;

import com.example.taskmanagement.model.LecturerInfo;
import com.example.taskmanagement.model.StudentInfo;
import com.example.taskmanagement.model.User;

import java.util.List;

public interface IUserService {
    boolean signUp(String username, String password, String role);
    String logIn(String username, String password);
    StudentInfo getStudentInfo(Long userId);
    LecturerInfo getLecturerInfo(Long userId);
    List<User> getAllUsers();
    User getUser(Long userId);
    Integer addStudentInfo(StudentInfo studentInfo);
    Integer addLecturerInfo(LecturerInfo lecturerInfo);
    Integer deleteAllUsers();
    Integer deleteUser(Long userId);
    Integer deleteStudentInfo(Long userId);
    Integer deleteLecturerInfo(Long userId);
    Integer updateUser(Long userId, User user);
    Integer updateStudentInfo(Long userId, StudentInfo studentInfo);
    Integer updateLecturerInfo(Long userId, LecturerInfo lecturerInfo);
}
