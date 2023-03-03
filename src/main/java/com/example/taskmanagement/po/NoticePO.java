package com.example.taskmanagement.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("notice")
public class NoticePO {
    @TableId(type = IdType.AUTO)
    private Long noticeId;
    private Long classroomId;
    private String content;
    private Date date;

    public NoticePO() {}

    public NoticePO(Long noticeId, Long classroomId, String content, Date date) {
        this.noticeId = noticeId;
        this.classroomId = classroomId;
        this.content = content;
        this.date = date;
    }
}
