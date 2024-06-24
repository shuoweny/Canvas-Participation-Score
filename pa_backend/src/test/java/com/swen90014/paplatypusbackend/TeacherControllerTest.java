package com.swen90014.paplatypusbackend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swen90014.paplatypusbackend.controller.TeacherController;

import com.swen90014.paplatypusbackend.domain.Teacher;
import com.swen90014.paplatypusbackend.service.impl.TeacherServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TeacherControllerTest {

    @Mock
    private TeacherServiceImpl teacherService;

    @InjectMocks
    private TeacherController teacherController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(teacherController).build();
    }

    @Test
    public void testGetAllTeachers() throws Exception {
        List<Teacher> teacherList = new ArrayList<>();
        Teacher teacher1 = new Teacher();
        Teacher teacher2 = new Teacher();
        teacherList.add(teacher1);
        teacherList.add(teacher2);

        when(teacherService.list()).thenReturn(teacherList);

        mockMvc.perform(get("/teacher/getAll"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.state").value(200))
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(jsonPath("$.data.length()").value(2));

        verify(teacherService, times(1)).list();
        verifyNoMoreInteractions(teacherService);
    }

    @Test
    public void testGetTeacherById() throws Exception {
        Long teacherId = 1L;
        Teacher teacher = new Teacher();
        teacher.setTeacherId(teacherId);

        when(teacherService.getById(teacherId)).thenReturn(teacher);

        mockMvc.perform(get("/teacher/getTeacherById/{id}", teacherId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.state").value(200))
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(jsonPath("$.data.teacherId").value(teacherId));

        verify(teacherService, times(1)).getById(teacherId);
        verifyNoMoreInteractions(teacherService);
    }

    @Test
    public void testGetTeacherByName() throws Exception {
        String name = "John Doe";
        List<Teacher> teacherList = new ArrayList<>();
        Teacher teacher1 = new Teacher();
        Teacher teacher2 = new Teacher();
        teacherList.add(teacher1);
        teacherList.add(teacher2);

        when(teacherService.getTeacherByName(name)).thenReturn(teacherList);

        mockMvc.perform(get("/teacher/getTeacherByName/{name}", name))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.state").value(200))
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(jsonPath("$.data.length()").value(2));

        verify(teacherService, times(1)).getTeacherByName(name);
        verifyNoMoreInteractions(teacherService);
    }

    @Test
    public void testGetTeacherByUserNumber() throws Exception {
        String sisUserId = "12345";
        List<Teacher> teacherList = new ArrayList<>();
        Teacher teacher1 = new Teacher();
        Teacher teacher2 = new Teacher();
        teacherList.add(teacher1);
        teacherList.add(teacher2);

        when(teacherService.getTeacherByUserNumber(sisUserId)).thenReturn(teacherList);

        mockMvc.perform(get("/teacher/getTeacherByUserNumber/{sis_user_id}", sisUserId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.state").value(200))
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(jsonPath("$.data.length()").value(2));

        verify(teacherService, times(1)).getTeacherByUserNumber(sisUserId);
        verifyNoMoreInteractions(teacherService);
    }

    @Test
    public void testGetTeacherByUserLogin() throws Exception {
        String loginId = "johndoe";
        List<Teacher> teacherList = new ArrayList<>();
        Teacher teacher1 = new Teacher();
        Teacher teacher2 = new Teacher();
        teacherList.add(teacher1);
        teacherList.add(teacher2);

        when(teacherService.getTeacherByUserLogin(loginId)).thenReturn(teacherList);

        mockMvc.perform(get("/teacher/getTeacherByUserLogin/{login_id}", loginId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.state").value(200))
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(jsonPath("$.data.length()").value(2));

        verify(teacherService, times(1)).getTeacherByUserLogin(loginId);
        verifyNoMoreInteractions(teacherService);
    }
}
