package com.example.taskmanagement.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("choice_question")
public class ChoiceQuestionPO {
    @TableId(type = IdType.AUTO)
    private Long choiceQuestionId;
    private Long taskId;
    private Integer sequenceNumber;
    private String title;
    private Integer type;
    private Integer score;

    public ChoiceQuestionPO() {}

    public ChoiceQuestionPO(Long choiceQuestionId, Long taskId, Integer sequenceNumber, String title, Integer type, Integer score) {
        this.choiceQuestionId = choiceQuestionId;
        this.taskId = taskId;
        this.sequenceNumber = sequenceNumber;
        this.title = title;
        this.type = type;
        this.score = score;
    }
}
