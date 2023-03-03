package com.example.taskmanagement.service.exception;

public class StudentNotInClassroomException extends BaseException{

    public StudentNotInClassroomException() {
    }

    public StudentNotInClassroomException(String message) {
        super(message);
    }

    public StudentNotInClassroomException(String message, Throwable cause) {
        super(message, cause);
    }

    public StudentNotInClassroomException(Throwable cause) {
        super(cause);
    }

    public StudentNotInClassroomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
