package com.example.taskmanagement.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.taskmanagement.po.NoticePO;
import org.apache.ibatis.annotations.*;

@Mapper
public interface NoticeMapper extends BaseMapper<NoticePO> {
}
