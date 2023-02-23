package com.example.taskmanagement.mapper;

import com.example.taskmanagement.model.Comment;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface CommentMapper {

    @Insert("insert into `comment` (`class_id`, `user_id`, `content`) " +
            "values(#{classId}, #{userId}, #{content})")
    Integer addComment(Long classId, Long userId, String content);

    @Delete("delete from `comment` where `class_id` = #{classId}")
    Integer deleteAllCommentsOfClassroom(Long classId);

    @Delete("delete from `comment` where `comment_id` = #{commentId}")
    Integer deleteCommentByCommentId(Long commentId);

    @Update("update `comment` set `class_id` = #{classId}, `user_id` = #{userId}, `content` = #{content} " +
            "where `comment_id` = #{commentId}")
    Integer updateCommentByCommentId(Long commentId, Long classId, Long userId, String content);

    @Select("select * from `comment` where `class_id` = #{classId}")
    List<Comment> getAllCommentsOfClassroom(Long classId);

    @Select("select * from `comment` where `comment_id` = #{commentId}")
    Comment getCommentByCommentId(Long commentId);
}
