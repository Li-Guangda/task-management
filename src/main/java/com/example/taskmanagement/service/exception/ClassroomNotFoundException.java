package com.example.taskmanagement.service.exception;

public class ClassroomNotFoundException extends BaseException{

    public ClassroomNotFoundException() {
    }

    public ClassroomNotFoundException(String message) {
        super(message);
    }

    public ClassroomNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClassroomNotFoundException(Throwable cause) {
        super(cause);
    }

    public ClassroomNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
