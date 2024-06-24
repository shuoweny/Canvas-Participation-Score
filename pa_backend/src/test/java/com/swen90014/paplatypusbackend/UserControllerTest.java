package com.swen90014.paplatypusbackend;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.swen90014.paplatypusbackend.controller.UserController;
import com.swen90014.paplatypusbackend.controller.utils.ResultUtil;
import com.swen90014.paplatypusbackend.domain.User;
import com.swen90014.paplatypusbackend.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private UserController userController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testGetAllUsers() throws Exception {
        User user1 = new User(1L, "John Doe", "https://example.com/avatar1.jpg", "Doe", "John", "token1");
        User user2 = new User(2L, "Jane Smith", "https://example.com/avatar2.jpg", "Smith", "Jane", "token2");
        List<User> userList = Arrays.asList(user1, user2);

        Mockito.when(userService.list()).thenReturn(userList);

        mockMvc.perform(get("/user/getAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new ResultUtil(200, userList, "SUCCESS"))));

        assertNotNull(userList);
        assertEquals(2, userList.size());
    }

    @Test
    public void testGetUserById() throws Exception {
        User user = new User(1L, "John Doe", "https://example.com/avatar1.jpg", "Doe", "John", "token1");

        Mockito.when(userService.getById(1L)).thenReturn(user);

        mockMvc.perform(get("/user/getUserById/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new ResultUtil(200, user, "SUCCESS"))));

        assertNotNull(user);
        assertEquals("John Doe", user.getName());
    }

    @Test
    public void testGetUserByToken() throws Exception {
        User user = new User(1L, "John Doe", "https://example.com/avatar1.jpg", "Doe", "John", "token1");

        Mockito.when(userService.getUserByToken("token1")).thenReturn(user);

        mockMvc.perform(get("/user/getUserByToken/{token}", "token1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new ResultUtil(200, user, "SUCCESS"))));

        assertNotNull(user);
        assertEquals("token1", user.getToken());
    }

    @Test
    public void testGetUserByName() throws Exception {
        User user1 = new User(1L, "John Doe", "https://example.com/avatar1.jpg", "Doe", "John", "token1");
        User user2 = new User(2L, "John Smith", "https://example.com/avatar2.jpg", "Smith", "John", "token2");
        List<User> userList = Arrays.asList(user1, user2);

        Mockito.when(userService.getUserByName("John")).thenReturn(userList);

        mockMvc.perform(get("/user/getUserByName/{name}", "John")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new ResultUtil(200, userList, "SUCCESS"))));

        assertNotNull(userList);
        assertEquals(2, userList.size());
    }
}
