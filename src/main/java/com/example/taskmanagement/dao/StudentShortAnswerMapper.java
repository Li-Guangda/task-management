package com.example.taskmanagement.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.taskmanagement.po.StudentShortAnswerPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudentShortAnswerMapper extends BaseMapper<StudentShortAnswerPO> {
    @Select("select `student_short_answer`.* " +
            "from `student_short_answer` " +
            "left join `short_answer_question` on `short_answer_question`.`short_answer_question_id` = `student_short_answer`.`short_answer_question_id` " +
            "where `short_answer_question`.`task_id` = #{taskId}")
    List<StudentShortAnswerPO> selectByTaskId(Long taskId);
}
