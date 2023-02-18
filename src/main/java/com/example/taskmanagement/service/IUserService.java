package com.example.taskmanagement.service;

import com.example.taskmanagement.model.User;

public interface IUserService {
    boolean signUp(String username, String password, String role);
    String logIn(String username, String password);
}
