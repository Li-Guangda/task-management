package com.example.taskmanagement.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.taskmanagement.dao.*;
import com.example.taskmanagement.po.ClassroomPO;
import com.example.taskmanagement.po.ClassroomStudentPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPublicKey;

@Component
public class PreAuthorizeHelper {

    @Value("${jwt.public.key}")
    RSAPublicKey publicKey;
    private JwtDecoder jwtDecoder;
    private ClassroomMapper classroomMapper;
    private UserMapper userMapper;
    private NoticeMapper noticeMapper;
    private TaskMapper taskMapper;
    private StudentTaskMapper studentTaskMapper;
    private ClassroomStudentMapper classroomStudentMapper;


    public boolean isClassroomLecturer(Long classroomId, String token) {
        ClassroomPO classroomPO = classroomMapper.selectById(classroomId);
        if (classroomPO == null)
            return false;
        return getUserIdFromToken(token) == classroomPO.getLecturerId();
    }

    public boolean isClassroomStudent(Long classroomId, String token) {
        QueryWrapper<ClassroomStudentPO> classroomStudentPOQueryWrapper = new QueryWrapper<>();
        classroomStudentPOQueryWrapper.eq("classroom_id", classroomId)
                                      .eq("student_id", getUserIdFromToken(token))
                                      .eq("joined", true);
        ClassroomStudentPO classroomStudentPO = classroomStudentMapper.selectOne(classroomStudentPOQueryWrapper);
        return classroomStudentPO != null;
    }

    public boolean isClassroomMember(Long classroomId, String token) {
        return isClassroomLecturer(classroomId, token) || isClassroomStudent(classroomId, token);
    }

    public boolean isUserSelf(Long userId, String token) {
        return userId == getUserIdFromToken(token);
    }

    /*
    public boolean isNoticePublisher(Long noticeId, String token) {
        return getUserIdFromToken(token) == noticeMapper.getPublisherIdOfNotice(noticeId);
    }

    public Boolean isTaskPublisher(Long taskId, String token) {
        return getUserIdFromToken(token) == taskMapper.getLecturIdOfTask(taskId);
    }

    public Boolean isTaskSubmitter(Long taskId, String token) {
        StudentTask studentTask = studentTaskMapper.getStudentTask(getUserIdFromToken(token), taskId);
        return studentTask != null;
    }

     */

    private Long getUserIdFromToken(String token) {
        return jwtDecoder
                .decode(token.substring("Bearer ".length()))
                .getClaim("userId");
    }

    @Qualifier("jwtDecoder")
    @Autowired
    public void setJwtDecoder(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Autowired
    public void setClassroomMapper(ClassroomMapper classroomMapper) {
        this.classroomMapper = classroomMapper;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setNoticeMapper(NoticeMapper noticeMapper) {
        this.noticeMapper = noticeMapper;
    }

    @Autowired
    public void setTaskMapper(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    @Autowired
    public void setStudentTaskMapper(StudentTaskMapper studentTaskMapper) {
        this.studentTaskMapper = studentTaskMapper;
    }

    @Autowired
    public void setClassroomStudentMapper(ClassroomStudentMapper classroomStudentMapper) {
        this.classroomStudentMapper = classroomStudentMapper;
    }
}
