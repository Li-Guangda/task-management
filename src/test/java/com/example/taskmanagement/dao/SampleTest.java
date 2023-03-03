package com.example.taskmanagement.dao;

import com.example.taskmanagement.po.UserPO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class SampleTest {
    @Autowired
    private UserMapper userMapper;
    private StudentInfoMapper studentInfoMapper;


    @Test
    public void testSelect() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("username", "dsdsd");
        UserPO userPO = userMapper.selectByMap(map).get(0);
        System.out.println(userPO);
    }

    @Autowired
    public void setStudentInfoMapper(StudentInfoMapper studentInfoMapper) {
        this.studentInfoMapper = studentInfoMapper;
    }
}
