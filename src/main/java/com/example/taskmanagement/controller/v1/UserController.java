package com.example.taskmanagement.controller.v1;

import com.example.taskmanagement.controller.response.Result;
import com.example.taskmanagement.controller.response.Token;
import com.example.taskmanagement.model.LecturerInfo;
import com.example.taskmanagement.model.StudentInfo;
import com.example.taskmanagement.model.User;
import com.example.taskmanagement.service.IUserService;
import com.example.taskmanagement.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private IUserService userService;

    @PostMapping("/user")
    public ResponseEntity<?> newUser(@RequestBody @Validated User user) {
        if (!userService.signUp(user.getUsername(), user.getPassword(), user.getRole()))
            return ResponseEntity.status(400).body(new Result<>("Sign up failed", null));
        return ResponseEntity.ok(new Result<>("Sign up successfully", null));
    }

    @PostMapping("/token")
    public ResponseEntity<?> getToken(@RequestBody @Validated User user) {
        String token = userService.logIn(user.getUsername(), user.getPassword());
        if (token == null) {
            return ResponseEntity.status(401).body(new Result<>("Verified failed", null));
        }
        return ResponseEntity.ok(new Result<>("Verified successfully", new Token(token)));
    }

    @PostMapping("/user/student/{userId}/info")
    public ResponseEntity<?> addStudentInfo(@PathVariable Long userId, @RequestBody @Validated StudentInfo studentInfo) {
        Integer res = userService.addStudentInfo(studentInfo);
        if (res != 1) {
            return ResponseEntity.status(400).body(new Result<>("Add student info failed", null));
        }
        return ResponseEntity.ok(new Result<>("Add student info successfully", null));
    }

    @PostMapping("/user/lecturer/{userId}/info")
    public ResponseEntity<?> addLecturerInfo(@PathVariable Long userId, @RequestBody @Validated LecturerInfo lecturerInfo) {
        Integer res = userService.addLecturerInfo(lecturerInfo);
        if (res != 1) {
            return ResponseEntity.status(400).body(new Result<>("Add lecturer info failed", null));
        }
        return ResponseEntity.ok(new Result<>("Add lecturer infoduccessfully", null));
    }

    @DeleteMapping("/users")
    public ResponseEntity<?> deleteAllUser() {
        userService.deleteAllUsers();
        return ResponseEntity.ok(new Result<>("Delete all users successfully", null));
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        Integer res = userService.deleteUser(userId);
        if (res != 1) {
            return ResponseEntity.status(400).body(new Result<>("Cannot delete user", null));
        }
        return ResponseEntity.ok(new Result<>("Delete user successfully", null));
    }

    @DeleteMapping("/user/student/{userId}/info")
    public ResponseEntity<?> deleteStudentInfo(@PathVariable Long userId) {
        Integer res = userService.deleteStudentInfo(userId);
        if (res != 1) {
            return ResponseEntity.status(400).body(new Result<>("Cannot delete student info", null));
        }
        return ResponseEntity.ok(new Result<>("Delete student info successfully", null));
    }

    @DeleteMapping("/user/lecturer/{userId}/info")
    public ResponseEntity<?> deleteLecturerInfo(@PathVariable Long userId) {
        Integer res = userService.deleteLecturerInfo(userId);
        if (res != 1) {
            return ResponseEntity.status(400).body(new Result<>("Cannot delete lecturer info", null));
        }
        return ResponseEntity.ok(new Result<>("Delete lecturer info successfully", null));
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody @Validated User user) {
        Integer res = userService.updateUser(userId, user);
        if (res != 1) {
            return ResponseEntity.status(400).body(new Result<>("Cannot update user", null));
        }
        return ResponseEntity.ok(new Result<>("Update user successfully", null));
    }

    @PutMapping("/user/student/{userId}/info")
    public ResponseEntity<?> updateStudentInfo(@PathVariable Long userId, @RequestBody @Validated StudentInfo studentInfo) {
       Integer res = userService.updateStudentInfo(userId, studentInfo);
       if (res != 1) {
           return ResponseEntity.status(400).body(new Result<>("Cannot update student info", null));
       }
       return ResponseEntity.ok(new Result<>("Update student info successfully", null));
    }

    @PutMapping("/user/lecturer/{userId}/info")
    public ResponseEntity<?> updateLecurerInfo(@PathVariable Long userId, @RequestBody @Validated LecturerInfo lecturerInfo) {
        Integer res = userService.updateLecturerInfo(userId, lecturerInfo);
        if (res != 1) {
            return ResponseEntity.status(400).body(new Result<>("Cannot update lecturer info", null));
        }
        return ResponseEntity.ok(new Result<>("Update lecturer info successfully", null));
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users == null) {
            return ResponseEntity.status(400).body(new Result<>("Cannot get any user", null));
        }
        return ResponseEntity.ok(new Result<>("Get all users successfully", users));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUser(@PathVariable Long userId) {
        User user = userService.getUser(userId);
        if (user == null) {
            return ResponseEntity.status(400).body(new Result<>("Cannot get user", null));
        }
        return ResponseEntity.ok(new Result<>("Get user successfully", user));
    }

    @GetMapping("/user/student/{userId}/info")
    public ResponseEntity<?> getStudentInfo(@PathVariable Long userId) {
        StudentInfo studentInfo = userService.getStudentInfo(userId);
        if (studentInfo == null) {
            return ResponseEntity.status(400).body(new Result<>("Cannot get student info", null));
        }
        return ResponseEntity.ok(new Result<>("Get student info successfully", studentInfo));
    }

    @GetMapping("/user/lecturer/{userId}/info")
    public ResponseEntity<?> getLecturerInfo(@PathVariable Long userId) {
        LecturerInfo lecturerInfo = userService.getLecturerInfo(userId);
        if (lecturerInfo == null) {
            return ResponseEntity.status(400).body(new Result<>("Cannot get lecturer info", null));
        }
        return ResponseEntity.ok(new Result<>("Get lecturer info successfully", lecturerInfo));
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}