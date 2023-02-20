package com.example.taskmanagement.mapper;

import com.example.taskmanagement.model.StudentInfo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface StudentInfoMapper {
    @Insert("insert into `student_info` (`student_id`, `university`, `student_number`, `avatar`, `name`, `gender`, `intro`) " +
            "values(#{studentId}, #{university}, #{studentNumber}, #{avatar}, #{name}, #{gender}, #{intro})")
    Integer addStudentInfo(Long studentId, String university, String studentNumber, String avatar,
                           String name, String gender, String intro);

    @Delete("delete from `student_info` where `student_id` = #{studentId}")
    Integer deleteStudentInfoById(Long studentId);

    @Update("update `student_info` set `university` = #{university}, `student_number` = #{studentNumber}, " +
            "`avatar` = #{avatar}, `name` = #{name}, `gender` = #{gender}, `intro` = #{intro} where `student_id` = #{userId}")
    Integer updateStudentInfoById(Long userId, String university, String studentNumber,
                                  String avatar, String name, String gender, String intro);

    @Select("select * from `student_info` where `student_id` = #{studentId}")
    StudentInfo getStudentInfoById(Long studentId);
}
