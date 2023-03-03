package com.example.taskmanagement.dto;

import lombok.Data;

@Data
public class UserInfo {
    private Long userId;
    private String name;
    private String avatar;

    public UserInfo() {
    }

    public UserInfo(Long userId, String name, String avatar) {
        this.userId = userId;
        this.name = name;
        this.avatar = avatar;
    }
}
