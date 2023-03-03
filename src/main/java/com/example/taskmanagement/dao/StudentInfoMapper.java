package com.example.taskmanagement.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.taskmanagement.po.StudentInfoPO;
import org.apache.ibatis.annotations.*;

@Mapper
public interface StudentInfoMapper extends BaseMapper<StudentInfoPO> {
}
