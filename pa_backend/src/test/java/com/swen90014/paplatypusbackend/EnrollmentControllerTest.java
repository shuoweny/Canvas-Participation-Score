package com.swen90014.paplatypusbackend;


import com.swen90014.paplatypusbackend.controller.EnrollmentController;
import com.swen90014.paplatypusbackend.domain.Enrollment;
import com.swen90014.paplatypusbackend.service.impl.EnrollmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class EnrollmentControllerTest {

    @Mock
    private EnrollmentServiceImpl enrollmentService;

    @InjectMocks
    private EnrollmentController enrollmentController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(enrollmentController).build();
    }

    @Test
    public void testGetAll_Success() throws Exception {
        List<Enrollment> enrollmentList = new ArrayList<>();
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setId(1L);
        enrollmentList.add(enrollment1);
        when(enrollmentService.list()).thenReturn(enrollmentList);

        mockMvc.perform(MockMvcRequestBuilders.get("/enrollment/getAll"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.state").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("SUCCESS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id").value(1)); // 验证响应数据中的第一个 Enrollment 的ID

        verify(enrollmentService, times(1)).list();
    }

    @Test
    public void testGetAll_Fail() throws Exception {
        when(enrollmentService.list()).thenReturn(new ArrayList<>());

        mockMvc.perform(MockMvcRequestBuilders.get("/enrollment/getAll"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.state").value(500))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("FAIL"));

        verify(enrollmentService, times(1)).list();
    }

    @Test
    public void testGetById_Success() throws Exception {
        Enrollment enrollment = new Enrollment();
        enrollment.setId(1L);
        when(enrollmentService.getById(1L)).thenReturn(enrollment);

        mockMvc.perform(MockMvcRequestBuilders.get("/enrollment/getAssignmentById/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.state").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("SUCCESS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(1)); // 验证响应数据中的 Enrollment 的ID

        verify(enrollmentService, times(1)).getById(1L);
    }

    @Test
    public void testGetById_Fail() throws Exception {
        when(enrollmentService.getById(1L)).thenReturn(null);

        // 发送 GET 请求到 /enrollment/getAssignmentById/1 端点
        mockMvc.perform(MockMvcRequestBuilders.get("/enrollment/getAssignmentById/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.state").value(500))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("FAIL"));

        verify(enrollmentService, times(1)).getById(1L);
    }

    @Test
    public void testGetEnrollmentByCourseId_Success() throws Exception {
        List<Enrollment> enrollmentList = new ArrayList<>();
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setId(1L);
        enrollmentList.add(enrollment1);
        when(enrollmentService.getEnrollmentByCourseId(1L)).thenReturn(enrollmentList);

        mockMvc.perform(MockMvcRequestBuilders.get("/enrollment/getEnrollmentByCourseId/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.state").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("SUCCESS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id").value(1)); // 验证响应数据中的第一个 Enrollment 的ID

        verify(enrollmentService, times(1)).getEnrollmentByCourseId(1L);
    }

    @Test
    public void testGetEnrollmentByCourseId_Fail() throws Exception {
        when(enrollmentService.getEnrollmentByCourseId(1L)).thenReturn(new ArrayList<>());

        mockMvc.perform(MockMvcRequestBuilders.get("/enrollment/getEnrollmentByCourseId/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.state").value(500))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("FAIL"));

        verify(enrollmentService, times(1)).getEnrollmentByCourseId(1L);
    }

    @Test
    public void testGetEnrollmentByUserId_Success() throws Exception {
        List<Enrollment> enrollmentList = new ArrayList<>();
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setId(1L);
        enrollmentList.add(enrollment1);
        when(enrollmentService.getEnrollmentByUserId(1L)).thenReturn(enrollmentList);

        mockMvc.perform(MockMvcRequestBuilders.get("/enrollment/getEnrollmentByUserId/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.state").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("SUCCESS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id").value(1)); // 验证响应数据中的第一个 Enrollment 的ID

        verify(enrollmentService, times(1)).getEnrollmentByUserId(1L);
    }

    @Test
    public void testGetEnrollmentByUserId_Fail() throws Exception {
        when(enrollmentService.getEnrollmentByUserId(1L)).thenReturn(new ArrayList<>());

        mockMvc.perform(MockMvcRequestBuilders.get("/enrollment/getEnrollmentByUserId/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.state").value(500))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("FAIL"));

        verify(enrollmentService, times(1)).getEnrollmentByUserId(1L);
    }
}

