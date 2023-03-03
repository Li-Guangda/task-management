package com.example.taskmanagement.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("comment")
public class CommentPO {
    @TableId(type = IdType.AUTO)
    private Long commentId;
    private Long classroomId;
    private Long userId;
    private String content;
    private Date date;

    public CommentPO() {}

    public CommentPO(Long commentId, Long classroomId, Long userId, String content, Date date) {
        this.commentId = commentId;
        this.classroomId = classroomId;
        this.userId = userId;
        this.content = content;
        this.date = date;
    }
}
