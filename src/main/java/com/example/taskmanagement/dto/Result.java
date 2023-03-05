package com.example.taskmanagement.dto;

import lombok.Data;

@Data
public class Result<T> {

    private String message;
    private T data;

    public Result() {}

    public Result(String message, T data) {
        this.message = message;
        this.data = data;
    }

    static public Result onlyMessage(String message) {
        return new Result(message, null);
    }
}
