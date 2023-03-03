package com.example.taskmanagement.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("question_option")
public class QuestionOptionPO {

    @TableId(type = IdType.AUTO)
    private Long questionOptionId;
    private Long choiceQuestionId;
    private Integer sequenceNumber;
    private String content;

    public QuestionOptionPO() {}

    public QuestionOptionPO(Long questionOptionId, Long choiceQuestionId, Integer sequenceNumber, String content) {
        this.questionOptionId = questionOptionId;
        this.choiceQuestionId = choiceQuestionId;
        this.sequenceNumber = sequenceNumber;
        this.content = content;
    }
}
