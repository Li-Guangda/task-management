package com.example.taskmanagement.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("classroom")
public class ClassroomPO {

    @TableId(type = IdType.AUTO)
    private Long classroomId;
    private Long lecturerId;
    private String className;
    private String classCode;

    public ClassroomPO() {}

    public ClassroomPO(Long classroomId, Long lecturerId, String className, String classCode) {
        this.classroomId = classroomId;
        this.lecturerId = lecturerId;
        this.className = className;
        this.classCode = classCode;
    }
}
