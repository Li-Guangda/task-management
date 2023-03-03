package com.example.taskmanagement.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("student_short_answer")
public class StudentShortAnswerPO {
    private Long shortAnswerQuestionId;
    private Long studentId;
    private String answer;
    private Integer score;

    public StudentShortAnswerPO() {}

    public StudentShortAnswerPO(Long shortAnswerQuestionId, Long studentId,
                                String answer, Integer score) {
        this.shortAnswerQuestionId = shortAnswerQuestionId;
        this.studentId = studentId;
        this.answer = answer;
        this.score = score;
    }
}
