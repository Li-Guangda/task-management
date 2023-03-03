package com.example.taskmanagement.dto;

import lombok.Data;

@Data
public class Token {
    private String token;

    public Token() {
    }

    public Token(String token) {
        this.token = token;
    }
}
