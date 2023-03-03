package com.example.taskmanagement.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class UserPO {
    @TableId(type = IdType.AUTO)
    private Long userId;
    private String role;
    private String username;
    private String password;

    public UserPO() {}

    public UserPO(Long userId, String role, String username, String password) {
        this.userId = userId;
        this.role = role;
        this.username = username;
        this.password = password;
    }
}
