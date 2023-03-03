package com.example.taskmanagement.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("student_info")
public class StudentInfoPO {
    @TableId(type = IdType.AUTO)
    private Long studentId;
    private String university;
    private String studentNumber;
    private String avatar;
    private String name;
    private String gender;
    private String intro;

    public StudentInfoPO() {}

    public StudentInfoPO(Long studentId, String university, String studentNumber,
                         String avatar, String name, String gender, String intro) {
        this.studentId = studentId;
        this.university = university;
        this.studentNumber = studentNumber;
        this.avatar = avatar;
        this.name = name;
        this.gender = gender;
        this.intro = intro;
    }
}
