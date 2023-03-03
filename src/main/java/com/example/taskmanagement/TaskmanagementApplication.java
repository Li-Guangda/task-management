package com.example.taskmanagement;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.taskmanagement.dao")
public class TaskmanagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskmanagementApplication.class, args);
    }

}
