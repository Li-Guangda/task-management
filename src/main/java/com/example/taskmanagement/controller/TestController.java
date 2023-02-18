package com.example.taskmanagement.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/* Controller just for test */
@RestController
@RequestMapping("/test/api/v1")
public class TestController {
    @GetMapping("/nothing")
    public String test() {
        return "No Problem";
    }

    @GetMapping("/student")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public String student() {
        return "Just For Student";
    }

    @GetMapping("/lecturer")
    @PreAuthorize("hasRole('ROLE_LECTURER')")
    public String lecturer() {
        return "Just For Lecturer";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String admin() {
        return "Just For Admin";
    }
}
