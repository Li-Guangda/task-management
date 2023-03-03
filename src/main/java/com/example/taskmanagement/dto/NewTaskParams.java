package com.example.taskmanagement.dto;

import lombok.Data;

import java.util.Date;

@Data
public class NewTaskParams {
    private Long classroomId;
    private String taskTitle;
    private String taskDesc;
    private Date dateStart;
    private Date dateEnd;
    private ChoiceQuestion[] choiceQuestions;
    private ShortQuestion[] shortQuestions;

    public NewTaskParams() {}

    public NewTaskParams(Long classId, String taskTitle, String taskDesc, Date dateEnd,
                         ChoiceQuestion[] choiceQuestions, ShortQuestion[] shortQuestions) {
        this.classroomId = classId;
        this.taskTitle = taskTitle;
        this.taskDesc = taskDesc;
        this.dateEnd = dateEnd;
        this.choiceQuestions = choiceQuestions;
        this.shortQuestions = shortQuestions;
    }
}