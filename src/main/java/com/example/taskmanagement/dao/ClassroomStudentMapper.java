package com.example.taskmanagement.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.taskmanagement.po.ClassroomStudentPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ClassroomStudentMapper extends BaseMapper<ClassroomStudentPO> {

}
