package com.example.taskmanagement.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("lecturer_info")
public class LecturerInfoPO {
    @TableId(type = IdType.AUTO)
    private Long lecturerId;
    private String university;
    private String position;
    private String avatar;
    private String name;
    private String gender;
    private String intro;

    public LecturerInfoPO() {}

    public LecturerInfoPO(Long lecturerId, String university, String position,
                          String avatar, String name, String gender, String intro) {
        this.lecturerId = lecturerId;
        this.university = university;
        this.position = position;
        this.avatar = avatar;
        this.name = name;
        this.gender = gender;
        this.intro = intro;
    }
}
