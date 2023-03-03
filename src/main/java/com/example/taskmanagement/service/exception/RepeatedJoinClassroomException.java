package com.example.taskmanagement.service.exception;

public class RepeatedJoinClassroomException extends BaseException{

    public RepeatedJoinClassroomException() {
    }

    public RepeatedJoinClassroomException(String message) {
        super(message);
    }

    public RepeatedJoinClassroomException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepeatedJoinClassroomException(Throwable cause) {
        super(cause);
    }

    public RepeatedJoinClassroomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
