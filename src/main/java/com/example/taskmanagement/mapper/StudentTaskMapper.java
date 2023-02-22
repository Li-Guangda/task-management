package com.example.taskmanagement.mapper;

import com.example.taskmanagement.model.StudentTask;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentTaskMapper {
    @Insert("insert into `student_task` values(#{studentId}, #{taskId}, #{score}, " +
            "#{remark}, #{attachment}, #{content})")
    Integer addStudentTask(Long studentId, Long taskId, Integer score, String remark, String attachment, String content);

    @Delete(("delete from `student_task` where `student_id` = #{studentId} and `task_id` = #{taskId}"))
    Integer deleteStudentTaskByStudentIdAndTaskId(Long studentId, Long taskId);

    @Update("update `student_task` set `score` = #{score}, `remark` = #{remark}, " +
            "`attachment` = #{attachment}, `content` = #{content} " +
            "where `student_id` = #{studentId} and `task_id` = #{taskId}")
    Integer updateStudentTaskByStudentIdAndTaskId(Long studentId, Long taskId, Integer score, String remark, String attachment, String content);

    @Select("select * from `student_task` where `student_id` = #{studentId} and `task_id` = #{taskId}")
    StudentTask getStudentTask(Long studentId, Long taskId);

    @Select("select * from `student_task` where `student_id` = #{studentId}")
    List<StudentTask> getStudentTasksByStudentId(Long studentId);

    @Select("select * from `class_student` " +
            "left join `student_task` on `class_student`.`student_id` = `student_task`.`student_id` " +
            "where `class_id` = #{classId} and `task_id` = #{taskId} ")
    List<StudentTask> getStuentTasksByClassIdAndTaskId(Long classId, Long taskId);
}
