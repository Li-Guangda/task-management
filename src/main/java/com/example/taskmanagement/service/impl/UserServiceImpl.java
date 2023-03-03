package com.example.taskmanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.taskmanagement.dao.LecturerInfoMapper;
import com.example.taskmanagement.dao.StudentInfoMapper;
import com.example.taskmanagement.dao.UserMapper;
import com.example.taskmanagement.dto.LecturerDetailedInfo;
import com.example.taskmanagement.dto.LogInParams;
import com.example.taskmanagement.dto.SignUpParams;
import com.example.taskmanagement.dto.StudentDetailedInfo;
import com.example.taskmanagement.po.LecturerInfoPO;
import com.example.taskmanagement.po.StudentInfoPO;
import com.example.taskmanagement.po.UserPO;
import com.example.taskmanagement.service.IUserService;
import com.example.taskmanagement.service.exception.UserAlreadyExistsException;
import com.example.taskmanagement.service.exception.UserNotFoundException;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    private UserMapper userMapper;
    private AuthenticationManager authenticationManager;
    private JwtEncoder jwtEncoder;
    private LecturerInfoMapper lecturerInfoMapper;
    private StudentInfoMapper studentInfoMapper;

    @Override
    @Transactional
    public void signUp(SignUpParams signUpParams) {
        var bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = "{bcrypt}" + bCryptPasswordEncoder.encode(signUpParams.getPassword());
        QueryWrapper<UserPO> userPOQueryWrapper = new QueryWrapper<>();
        userPOQueryWrapper.eq("username", signUpParams.getUsername());
        if (userMapper.exists(userPOQueryWrapper)) {
            throw new UserAlreadyExistsException();
        }

        UserPO user = new UserPO();
        user.setUsername(signUpParams.getUsername());
        user.setPassword(encodedPassword);
        user.setRole(signUpParams.getRole());
        userMapper.insert(user);

        if (signUpParams.getRole().equals("ROLE_STUDENT")) {
            StudentInfoPO studentInfoPO = new StudentInfoPO();
            studentInfoPO.setStudentId(user.getUserId());
            studentInfoMapper.insert(studentInfoPO);
        } else if (signUpParams.getRole().equals("ROLE_LECTURER")) {
            LecturerInfoPO lecturerInfoPO = new LecturerInfoPO();
            lecturerInfoPO.setLecturerId(user.getUserId());
            lecturerInfoMapper.insert(lecturerInfoPO);
        }
    }

    @Override
    @Transactional
    public String logIn(LogInParams logInParams) {
        Authentication beforeAuthentication = new UsernamePasswordAuthenticationToken(
                logInParams.getUsername(),
                logInParams.getPassword()
        );
        Authentication afterAuthentication;
        try {
            afterAuthentication = authenticationManager.authenticate(beforeAuthentication);
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(afterAuthentication);
        } catch (AuthenticationException authenticationException) {
            throw new UserNotFoundException();
        }

        String scope = afterAuthentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        Instant now = Instant.now();
        long expiry = 36000L;

        QueryWrapper<UserPO> userPOQueryWrapper = new QueryWrapper<>();
        userPOQueryWrapper.eq("username", logInParams.getUsername());
        UserPO userPO = userMapper.selectOne(userPOQueryWrapper);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(logInParams.getUsername())
                .claim("authorities", scope)
                .claim("userId", userPO.getUserId())
                .build();
        String token = this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return token;
    }

    @Override
    public StudentDetailedInfo getStudentInfo(Long userId) {
        StudentInfoPO studentInfoPO =  studentInfoMapper.selectById(userId);
        return new StudentDetailedInfo(
                studentInfoPO.getStudentId(),
                studentInfoPO.getUniversity(),
                studentInfoPO.getStudentNumber(),
                studentInfoPO.getAvatar(),
                studentInfoPO.getName(),
                studentInfoPO.getGender(),
                studentInfoPO.getIntro()
        );
    }

    @Override
    public LecturerDetailedInfo getLecturerInfo(Long userId) {
        LecturerInfoPO lecturerInfoPO = lecturerInfoMapper.selectById(userId);
        return new LecturerDetailedInfo(
                lecturerInfoPO.getLecturerId(),
                lecturerInfoPO.getUniversity(),
                lecturerInfoPO.getPosition(),
                lecturerInfoPO.getAvatar(),
                lecturerInfoPO.getName(),
                lecturerInfoPO.getGender(),
                lecturerInfoPO.getIntro()
        );
    }

    @Override
    public void updateStudentInfo(StudentDetailedInfo studentDetailedInfo) {
        studentInfoMapper.updateById(new StudentInfoPO(
                studentDetailedInfo.getStudentId(),
                studentDetailedInfo.getUniversity(),
                studentDetailedInfo.getStudentNumber(),
                studentDetailedInfo.getAvatar(),
                studentDetailedInfo.getName(),
                studentDetailedInfo.getGender(),
                studentDetailedInfo.getIntro()
        ));
    }

    @Override
    public void updateLecturerInfo(LecturerDetailedInfo lecturerDetailedInfo) {
        lecturerInfoMapper.updateById(new LecturerInfoPO(
                lecturerDetailedInfo.getLecturerId(),
                lecturerDetailedInfo.getUniversity(),
                lecturerDetailedInfo.getPosition(),
                lecturerDetailedInfo.getAvatar(),
                lecturerDetailedInfo.getName(),
                lecturerDetailedInfo.getGender(),
                lecturerDetailedInfo.getIntro()
        ));
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setJwtEncoder(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    @Autowired
    public void setLecturerInfoMapper(LecturerInfoMapper lecturerInfoMapper) {
        this.lecturerInfoMapper = lecturerInfoMapper;
    }

    @Autowired
    public void setStudentInfoMapper(StudentInfoMapper studentInfoMapper) {
        this.studentInfoMapper = studentInfoMapper;
    }
}
