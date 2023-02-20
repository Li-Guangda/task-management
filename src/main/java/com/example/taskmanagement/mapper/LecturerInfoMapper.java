package com.example.taskmanagement.mapper;

import com.example.taskmanagement.model.LecturerInfo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface LecturerInfoMapper {

    @Insert("insert into `lecturer_info` (`lecturer_id`, `university`, `position`, `avatar`, `name`, `gender`, `intro`) " +
            "values(#{lecturerId}, #{university}, #{position}, #{avatar}, #{name}, #{gender}, #{intro})")
    Integer addLecturerInfo(Long lecturerId, String university, String position, String avatar, String name, String gender, String intro);

    @Delete("delete from `lecturer_info` where `lecturer_id` = #{lecturerId}")
    Integer deleteLecturerInfoById(Long lecturerId);

    @Update("update `lecturer_info` set `university` = #{university}, `position` = #{position}, " +
            "`avatar` = #{avatar}, `name` = #{name}, `gender` = #{gender}, `intro` = #{intro} where `lecturer_id` = #{userId}")
    Integer updateLecturerInfoById(Long userId, String university, String position, String avatar, String name, String gender, String intro);

    @Select("select * from `lecturer_info` where `lecturer_id` = #{lecturerId}")
    LecturerInfo getLecturerInfoById(Long lecturerId);
}
