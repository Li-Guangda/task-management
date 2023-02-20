package com.example.taskmanagement.service.impl;

import com.example.taskmanagement.mapper.LecturerInfoMapper;
import com.example.taskmanagement.mapper.StudentInfoMapper;
import com.example.taskmanagement.mapper.UserMapper;
import com.example.taskmanagement.model.LecturerInfo;
import com.example.taskmanagement.model.StudentInfo;
import com.example.taskmanagement.model.User;
import com.example.taskmanagement.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    private UserMapper userMapper;
    private StudentInfoMapper studentInfoMapper;
    private LecturerInfoMapper lecturerInfoMapper;
    private AuthenticationManager authenticationManager;
    private JwtEncoder jwtEncoder;

    @Override
    public boolean signUp(String username, String password, String role) {
        var bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = "{bcrypt}" + bCryptPasswordEncoder.encode(password);
        Integer res = userMapper.addUser(username, encodedPassword, role);
        if (res != 1)
            return false;
        return true;
    }

    @Override
    public Integer updateUser(Long userId, User user) {
        return userMapper.updateUserByUserId(
                userId,
                user.getUsername(),
                user.getPassword(),
                user.getRole()
        );
    }

    @Override
    public Integer updateStudentInfo(Long userId, StudentInfo studentInfo) {
        return studentInfoMapper.updateStudentInfoById(
            userId,
            studentInfo.getUniversity(),
            studentInfo.getStudentNumber(),
            studentInfo.getAvatar(),
            studentInfo.getName(),
            studentInfo.getGender(),
            studentInfo.getIntro()
        );
    }

    @Override
    public Integer updateLecturerInfo(Long userId, LecturerInfo lecturerInfo) {
        return lecturerInfoMapper.updateLecturerInfoById(
            userId,
            lecturerInfo.getUniversity(),
            lecturerInfo.getPosition(),
            lecturerInfo.getAvatar(),
            lecturerInfo.getName(),
            lecturerInfo.getGender(),
            lecturerInfo.getIntro()
        );
    }

    @Override
    public Integer deleteStudentInfo(Long userId) {
        return studentInfoMapper.deleteStudentInfoById(userId);
    }

    @Override
    public Integer deleteLecturerInfo(Long userId) {
        return lecturerInfoMapper.deleteLecturerInfoById(userId);
    }

    @Override
    public String logIn(String username, String password) {
        Authentication beforeAuthentication = new UsernamePasswordAuthenticationToken(username, password);
        Authentication afterAuthentication;
        try {
            afterAuthentication = authenticationManager.authenticate(beforeAuthentication);
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(afterAuthentication);
        } catch (AuthenticationException e) {
            return null;
        }

        String scope = afterAuthentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        Instant now = Instant.now();
        long expiry = 36000L;

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(username)
                .claim("authorities", scope)
                .build();
        String token = this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return token;
    }

    @Override
    public Integer deleteAllUsers() {
        return userMapper.deleteAllUsers();
    }

    @Override
    public Integer deleteUser(Long userId) {
        return userMapper.deleteUserByUserId(userId);
    }

    @Override
    public StudentInfo getStudentInfo(Long userId) {
        return studentInfoMapper.getStudentInfoById(userId);
    }

    @Override
    public Integer addStudentInfo(StudentInfo studentInfo) {
         return studentInfoMapper.addStudentInfo(
                studentInfo.getStudentId(),
                studentInfo.getUniversity(),
                studentInfo.getStudentNumber(),
                studentInfo.getAvatar(),
                studentInfo.getName(),
                studentInfo.getGender(),
                studentInfo.getIntro()
        );
    }

    @Override
    public Integer addLecturerInfo(LecturerInfo lecturerInfo) {
        return lecturerInfoMapper.addLecturerInfo(
            lecturerInfo.getLecturerId(),
            lecturerInfo.getUniversity(),
            lecturerInfo.getPosition(),
            lecturerInfo.getAvatar(),
            lecturerInfo.getName(),
            lecturerInfo.getGender(),
            lecturerInfo.getIntro()
        );
    }

    @Override
    public User getUser(Long userId) {
        return userMapper.getUserByUserId(userId);
    }

    @Override
    public LecturerInfo getLecturerInfo(Long userId) {
        return lecturerInfoMapper.getLecturerInfoById(userId);
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.getAllUsers();
    }

    @Autowired
    public void setJwtEncoder(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    @Autowired
    public void setStudentInfoMapper(StudentInfoMapper studentInfoMapper) {
        this.studentInfoMapper = studentInfoMapper;
    }

    @Autowired
    public void setLecturerInfoMapper(LecturerInfoMapper lecturerInfoMapper) {
        this.lecturerInfoMapper = lecturerInfoMapper;
    }
}
