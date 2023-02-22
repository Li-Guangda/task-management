package com.example.taskmanagement.mapper;

import com.example.taskmanagement.model.Task;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface TaskMapper {

    @Insert("insert into `task` (`class_id`, `task_title`, `task_desc`, `date_end`) " +
            "values(#{classId}, #{taskTitle}, #{taskDesc}, #{dateEnd})")
    Integer addTask(Long classId, String taskTitle, String taskDesc, Date dateEnd);
    @Delete("delete from `task` where `task_id` = #{taskId}")
    Integer deleteTaskById(Long taskId);
    @Delete("delete from `task` where `class_id` = #{classId}")
    Integer deleteAllTasksOfClass(Long classId);
    @Update("update `task` set `class_id` = #{classId}, `task_title` = #{taskTitle}, `task_desc` = #{taskDesc}, " +
            "`date_start` = #{dateStart}, `date_end` = #{dateEnd} " +
            "where `task_id` = #{taskId}")
    Integer updateTaskById(Long taskId, Long classId, String taskTitle, String taskDesc, Date dateStart, Date dateEnd);
    @Select("select * from `task` where `task_id` = #{taskId}")
    Task getTaskById(Long taskId);
    @Select("select * from `task` where `class_id` = #{classId}")
    List<Task> getAllTasksOfClass(Long classId);
}
