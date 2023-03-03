package com.example.taskmanagement.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("classroom_student")
public class ClassroomStudentPO {
    private Long classroomId;
    private Long studentId;
    private boolean joined;
    public ClassroomStudentPO() {}
    public ClassroomStudentPO(Long classroomId, Long studentId, boolean joined) {
        this.classroomId = classroomId;
        this.studentId = studentId;
        this.joined = joined;
    }
}
