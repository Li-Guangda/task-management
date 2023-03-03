package com.example.taskmanagement.service.exception;

public class StudentAlreadyInClassroomException extends BaseException{
    public StudentAlreadyInClassroomException() {
    }

    public StudentAlreadyInClassroomException(String message) {
        super(message);
    }

    public StudentAlreadyInClassroomException(String message, Throwable cause) {
        super(message, cause);
    }

    public StudentAlreadyInClassroomException(Throwable cause) {
        super(cause);
    }

    public StudentAlreadyInClassroomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
