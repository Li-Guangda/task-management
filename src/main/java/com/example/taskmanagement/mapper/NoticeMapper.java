package com.example.taskmanagement.mapper;

import com.example.taskmanagement.model.Notice;
import org.apache.ibatis.annotations.*;
import org.apache.juli.logging.Log;

import java.util.Date;
import java.util.List;

@Mapper
public interface NoticeMapper {

    @Insert("insert into `notice` (`class_id`, `content`, `date`) " +
            "values(#{classId}, #{content}, #{date})")
    Integer addNotice(Long classId, String content, Date date);

    @Delete("delete from `notice`")
    Integer deleteAllNotices();

    @Delete("delete from `notice` where `notice_id` = #{noticeId}")
    Integer deleteNoticeByNoticeId(Long noticeId);

    @Update("update `notice` set `class_id` = #{classId}, `content` = #{content}, `date` = #{date} " +
            "where `notice_id` = #{noticeId}")
    Integer updateNoticeByNoticeId(Long noticeId, Long classId, String content, Date date);

    @Select("select * from `notice` where `class_id` = #{classId}")
    List<Notice> getNoticesByClassId(Long classId);

    @Select("select * from `notice` where `notice_id` = #{noticeId}")
    Notice getNoticeByNoticeId(Long noticeId);
}
