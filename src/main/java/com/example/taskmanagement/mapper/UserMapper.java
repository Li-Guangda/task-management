package com.example.taskmanagement.mapper;

import com.example.taskmanagement.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Insert("insert into `user` (`username`, `password`, `role`) values(#{username}, #{password}, #{role})")
    Integer addUser(String username, String password, String role);

    @Delete("delete from `user` where `user_id` = #{userid}")
    Integer deleteUserByUserId(Long userId);

    @Delete("delete from `user`")
    Integer deleteAllUsers();

    @Update("update `user` set `username` = #{username}, `password` = #{password}, `role` = #{role} where `user_id` = #{userId}")
    Integer updateUserByUserId(Long userId, String username, String password, String role);

    @Select("select * from `user` where `username` = #{username}")
    User getUserByUsername(String username);

    @Select("select * from user where `user_id` = #{userId}")
    User getUserByUserId(Long userId);

    @Select("select * from `user`")
    List<User> getAllUsers();
}
