package com.example.taskmanagement.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("short_answer_question")
public class ShortAnswerQuestionPO {
    @TableId(type = IdType.AUTO)
    private Long shortAnswerQuestionId;
    private Long taskId;
    private Integer sequenceNumber;
    private String title;
    private Integer score;

    public ShortAnswerQuestionPO() {}

    public ShortAnswerQuestionPO(Long shortAnswerQuestionId, Long taskId, Integer sequenceNumber,
                                 String title, Integer score) {
        this.shortAnswerQuestionId = shortAnswerQuestionId;
        this.taskId = taskId;
        this.sequenceNumber = sequenceNumber;
        this.title = title;
        this.score = score;
    }
}
