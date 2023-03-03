package com.example.taskmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class SignUpParams {

    @NotBlank(message = "The username cannot be blank")
    private String username;
    @Length(min = 8, message = "The password is at least 8 characters in length")
    private String password;
    @NotBlank(message = "The role cannot be blank")
    private String role;

    public SignUpParams() {}

    public SignUpParams(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
