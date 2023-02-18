package com.example.taskmanagement.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class User {
    @NotNull(message = "User id cannot be null")
    @Min(value = 0, message = "User id must be equal or greater than 0, but 0 for meaningless")
    private Long userId;
    @NotBlank(message = "User role cannot be blank")
    private String role;
    @NotBlank(message = "Username cannot be blank")
    private String username;
    @Length(min = 8, max = 16, message = "The password must be 8 to 16 characters in length")
    private String password;

    public User(Long userId, String role, String username, String password) {
        this.userId = userId;
        this.role = role;
        this.username = username;
        this.password = password;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
