package com.example.taskmanagement.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("choice_question_answer")
public class ChoiceQuestionAnswerPO {
    private Long choiceQuestionId;
    private Long questionOptionId;

    public ChoiceQuestionAnswerPO() {}

    public ChoiceQuestionAnswerPO(Long choiceQuestionId, Long questionOptionId) {
        this.choiceQuestionId = choiceQuestionId;
        this.questionOptionId = questionOptionId;
    }
}
