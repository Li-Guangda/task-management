package com.example.taskmanagement.mapper;

import com.example.taskmanagement.model.Classroom;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ClassroomMapper {

    @Insert("insert into `classroom` (`lecturer_id`, `class_name`, `class_code`) " +
            "values(#{lecturerId}, #{className}, #{classCode})")
    Integer addClassroom(Long lecturerId, String className, String classCode);

    @Insert("insert into `classroom_student` (`class_id`, `student_id`) " +
            "values(#{classId}, #{studentId})")
    Integer addStudentToClass(Long classId, Long studentId);

    @Delete("delete from `classroom`")
    Integer deleteAllClassrooms();

    @Delete("delete from `classroom` where `class_id` = #{classId}")
    Integer deleteClassroomByClassId(Long classId);

    @Update("update `classroom` set `class_name` = #{className} where `class_id` = #{classId}")
    Integer updateClassroomByClassId(Long classId, String className);

    @Select("select * from `classroom`")
    List<Classroom> getAllClassrooms();

    @Select("select * from `classroom` where `lecturer_id` = #{lecturerId}")
    List<Classroom> getAllClassroomsByLecturerId(Long lecturerId);

    @Select("select * from `classroom` where `class_id` = #{classId}")
    Classroom getClassroomByClassId(Long classId);

    @Select("select count(`class_id`) from `classroom`")
    Integer getClassroomCount();
}
