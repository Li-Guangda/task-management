package com.example.taskmanagement.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("student_task")
public class StudentTaskPO {

    @TableId(type = IdType.AUTO)
    private Long taskId;
    private Long studentId;
    private String remark;
    private boolean isChecked;

    public StudentTaskPO() {}

    public StudentTaskPO(Long taskId, Long studentId, String remark, boolean isChecked) {
        this.taskId = taskId;
        this.studentId = studentId;
        this.remark = remark;
        this.isChecked = isChecked;
    }
}
