package com.example.taskmanagement.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("task")
public class TaskPO {
    @TableId(type = IdType.AUTO)
    private Long taskId;
    private Long classroomId;
    private String taskTitle;
    private String taskDesc;
    private Date dateStart;
    private Date dateEnd;

    public TaskPO() {}

    public TaskPO(Long taskId, Long classroomId, String taskTitle, String taskDesc, Date dateStart, Date dateEnd) {
        this.taskId = taskId;
        this.classroomId = classroomId;
        this.taskTitle = taskTitle;
        this.taskDesc = taskDesc;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }
}
