package com.example.taskmanagement.controller;

import com.example.taskmanagement.controller.response.Result;
import com.example.taskmanagement.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
public class UserControllerTests {

    final private ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;

    @Test
    public void userTest() throws Exception {
        String requestBody = this.objectMapper.writeValueAsString(new User(0L, "ROLE_STUDENT", "username", "password"));
        String responseBody = this.objectMapper.writeValueAsString(new Result<>("Sign up successfully", null));
        this.mockMvc
                .perform(post("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody))
                .andDo(document("user"));

        requestBody = this.objectMapper.writeValueAsString(new User(0L, "ROLE_STUDENT", "", "password"));
        responseBody = this.objectMapper.writeValueAsString(new Result<>("Username cannot be blank", null));
        this.mockMvc
                .perform(post("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                .andExpect(status().is(400))
                .andExpect(content().json(responseBody));
    }

    @Test
    public void tokenTest() throws Exception {
        String requestBody = this.objectMapper.writeValueAsString(new User(0L, "ROLE_STUDENT", "username", "password"));
        this.mockMvc
                .perform(post("/api/v1/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                )
                .andExpect(status().isOk())
                .andDo(document("token"));
    }

    @Autowired
    public void setMockMvc(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }
}
