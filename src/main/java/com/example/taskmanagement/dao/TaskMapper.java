package com.example.taskmanagement.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.taskmanagement.po.TaskPO;
import org.apache.ibatis.annotations.*;

@Mapper
public interface TaskMapper extends BaseMapper<TaskPO> {
}
