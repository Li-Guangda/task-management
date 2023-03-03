package com.example.taskmanagement.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.taskmanagement.po.CommentPO;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CommentMapper extends BaseMapper<CommentPO> {
}
