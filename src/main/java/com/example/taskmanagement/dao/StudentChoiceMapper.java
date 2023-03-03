package com.example.taskmanagement.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.taskmanagement.po.StudentChoicePO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudentChoiceMapper extends BaseMapper<StudentChoicePO> {
    @Select("select `student_choice`.`student_id`, `student_choice`.`question_option_id` " +
            "from `student_choice` " +
            "left join `choice_question` on `question_option`.`question_id` = `choice_question`.`choice_question_id` " +
            "left join `task` on `choice_question`.`task_id` = `task`.`task_id` " +
            "where `task`.`task_id` = #{taskId}")
    List<StudentChoicePO> selectByTaskId(Long taskId);
}
