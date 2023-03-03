package com.example.taskmanagement.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("student_choice")
public class StudentChoicePO {

    @TableId(type = IdType.AUTO)
    private Long studentId;
    private Long questionOptionId;

    public StudentChoicePO() {}

    public StudentChoicePO(Long studentId, Long questionOptionId) {
        this.studentId = studentId;
        this.questionOptionId = questionOptionId;
    }
}
