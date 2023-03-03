package com.example.taskmanagement.controller.v1;

import com.example.taskmanagement.dto.*;
import com.example.taskmanagement.service.exception.UserAlreadyExistsException;
import com.example.taskmanagement.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private UserServiceImpl userServiceImpl;

    @PostMapping("/user")
    public ResponseEntity<Result> signUp(@RequestBody SignUpParams signUpParams) {
        Integer code;
        String message;
        try {
            userServiceImpl.signUp(signUpParams);
            code = 200;
            message = "Signed up successfully";
        } catch (UserAlreadyExistsException e) {
            code = 400;
            message = "The username already exists";
        }
        return ResponseEntity.status(code).body(Result.onlyMessage(message));
    }

    @PostMapping("/user/token")
    public ResponseEntity<Result> logIn(@RequestBody LogInParams logInParams) {
        Integer code;
        String message;
        String token = null;
        try {
            token = userServiceImpl.logIn(logInParams);
            code = 200;
            message = "Verified successfully";
        } catch (UsernameNotFoundException e) {
            code = 401;
            message = "The username or password is incorrect";
        }
        return ResponseEntity.status(code).body(new Result(message, new Token(token)));
    }

    @GetMapping("/user/student/{userId}/info")
    public Result getStudentInfo(@PathVariable Long userId) {
        StudentDetailedInfo studentDetailedInfo = userServiceImpl.getStudentInfo(userId);
        return new Result<>("Got the student info successfully", studentDetailedInfo);
    }

    @GetMapping("/user/lecturer/{userId}/info")
    public Result getLecturerInfo(@PathVariable Long userId) {
        LecturerDetailedInfo lecturerDetailedInfo = userServiceImpl.getLecturerInfo(userId);
        return new Result<>("Got the lecturer info successfully", lecturerDetailedInfo);
    }

    @PutMapping("/user/student/{userId}/info")
    @PreAuthorize("hasRole('ROLE_STUDENT') and @preAuthorizeHelper.isUserSelf(#userId, #token)")
    public Result updateStudentInfo(@PathVariable Long userId,
                                    @RequestBody StudentDetailedInfo studentDetailedInfo,
                                    @RequestHeader("Authorization") String token) {
        userServiceImpl.updateStudentInfo(studentDetailedInfo);
        return Result.onlyMessage("Updated the student info successfully");
    }

    @PutMapping("/user/lecturer/{userId}/info")
    @PreAuthorize("hasRole('ROLE_LECTURER') and @preAuthorizeHelper.isUserSelf(#userId, #token)")
    public Result updateLecturerInfo(@PathVariable Long userId,
                                     @RequestBody LecturerDetailedInfo lecturerDetailedInfo,
                                     @RequestHeader("Authorization") String token)  {
        userServiceImpl.updateLecturerInfo(lecturerDetailedInfo);
        return Result.onlyMessage("Updated the lecturer info successfully");
    }

    /*
    @DeleteMapping("/user/student/{userId}/info")
    public Result deleteStudentInfo(@PathVariable Long userId,
                                    @RequestHeader("Authorization") String token) {
        userServiceImpl.deleteStudentInfo(userId);
        return Result.onlyMessage("Deleted the student info successfully");
    }

    @DeleteMapping("/user/lecturer/{userId}/info")
    public Result deleteLecturerInfo(@PathVariable Long userId,
                                     @RequestHeader("Authorization") String token) {
        userServiceImpl.deleteLecturerInfo(userId);
        return Result.onlyMessage("Deleted the lecturer info successfully");
    }
     */

    @Autowired
    public void setUserServiceImpl(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }
}
