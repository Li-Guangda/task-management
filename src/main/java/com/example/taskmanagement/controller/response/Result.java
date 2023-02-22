package com.example.taskmanagement.controller.response;

public class Result<T> {
    private String message;
    private T data;

    public Result(String message, T data) {
        this.message = message;
        this.data = data;
    }

    static public Result onlyMessage(String message) {
        return new Result(message, null);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
