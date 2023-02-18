package com.example.taskmanagement.mapper;

import com.example.taskmanagement.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO user (username, password, role) VALUES(#{username}, #{password}, #{role})")
    Integer addUser(String username, String password, String role);

    @Delete("DELETE FROM user WHERE username = #{username}")
    Integer deleteUserByUsername(String username);

    @Delete("DELETE FROM user WHERE user_id = #{userId}")
    Integer deleteUserByUserId(Integer userId);

    @Update("UPDATE user SET username = #{newUsername}, password = #{newPassword}, role = #{role} WHERE username = #{username}")
    Integer updateUserByUsername(String username, String newUsername, String newPassword, String role);

    @Update("UPDATE user SET username = #{newUsername}, password = #{newPassword}, role = #{role} WHERE user_id = #{userId}")
    Integer updateUserByUserId(String userId, String newUsername, String newPassword, String role);

    @Select("SELECT user_id, username, password, role FROM user WHERE username = #{username}")
    User getUserByUsername(String username);

    @Select(("SELECT user_id, username, password, role FROM user WHERE user_id = #{userId}"))
    User getUserByUserId(Integer userId);
}
