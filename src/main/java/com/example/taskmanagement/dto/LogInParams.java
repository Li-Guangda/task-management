package com.example.taskmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class LogInParams {

    @NotBlank(message = "The username can not be blank")
    private String username;
    @Length(message = "The password is at least 8 characters in length")
    private String password;

    public LogInParams() {}

    public LogInParams(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
