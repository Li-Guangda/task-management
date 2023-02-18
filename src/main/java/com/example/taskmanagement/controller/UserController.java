package com.example.taskmanagement.controller;

import com.example.taskmanagement.controller.response.Result;
import com.example.taskmanagement.controller.response.Token;
import com.example.taskmanagement.model.User;
import com.example.taskmanagement.service.IUserService;
import com.example.taskmanagement.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private IUserService userService;

    @PostMapping("/user")
    public ResponseEntity<?> user(@RequestBody @Validated User user) {
        if (!userService.signUp(user.getUsername(), user.getPassword(), user.getRole()))
            return ResponseEntity.status(500).body(new Result<>("Sign up failed", null));
        return ResponseEntity.ok(new Result<>("Sign up successfully", null));
    }

    @PostMapping("/token")
    public ResponseEntity<?> token(@RequestBody @Validated User user) {
        String token = userService.logIn(user.getUsername(), user.getPassword());
        if (token == null) {
            return ResponseEntity.status(401).body(new Result<>("Verified failed", null));
        }
        return ResponseEntity.ok(new Result<>("Verified successfully", new Token(token)));
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}