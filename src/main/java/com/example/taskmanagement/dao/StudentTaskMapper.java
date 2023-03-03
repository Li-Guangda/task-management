package com.example.taskmanagement.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.taskmanagement.po.StudentTaskPO;
import org.apache.ibatis.annotations.*;

@Mapper
public interface StudentTaskMapper extends BaseMapper<StudentTaskPO> {
}
