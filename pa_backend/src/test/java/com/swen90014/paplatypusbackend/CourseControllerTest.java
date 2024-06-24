package com.swen90014.paplatypusbackend;

import com.swen90014.paplatypusbackend.controller.CourseController;
import com.swen90014.paplatypusbackend.domain.Course;
import com.swen90014.paplatypusbackend.service.impl.CourseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CourseControllerTest {

    @InjectMocks
    private CourseController courseController;

    @Mock
    private CourseServiceImpl courseService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();
    }

    @Test
    void testGetAll() throws Exception {
        List<Course> mockCourses = new ArrayList<>();
        Course course1 = new Course();
        course1.setId(1L);
        course1.setName("Course 1");
        mockCourses.add(course1);

        when(courseService.list()).thenReturn(mockCourses);

        mockMvc.perform(get("/course/getAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value(200))
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(jsonPath("$.data[0].name").value("Course 1"));
    }

    @Test
    void testGetById() throws Exception {
        Course mockCourse = new Course();
        mockCourse.setCourse_id(1L);
        mockCourse.setName("Course 1");

        when(courseService.getById(1L)).thenReturn(mockCourse);

        mockMvc.perform(get("/course/getCourseById/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value(200))
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(jsonPath("$.data.name").value("Course 1"));
    }

    @Test
    void testGetCourseByCourseName() throws Exception {
        List<Course> mockCourses = new ArrayList<>();
        Course course1 = new Course();
        course1.setId(1L);
        course1.setName("Course 1");
        mockCourses.add(course1);

        when(courseService.getCourseByCourseName("Course 1")).thenReturn(mockCourses);

        mockMvc.perform(get("/course/getCourseByCourseName/{name}", "Course 1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value(200))
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(jsonPath("$.data[0].name").value("Course 1"));
    }

    @Test
    void testGetCourseByCourseCode() throws Exception {
        List<Course> mockCourses = new ArrayList<>();
        Course course1 = new Course();
        course1.setId(1L);
        course1.setName("Course 1");
        mockCourses.add(course1);

        when(courseService.getCourseByCourseCode("C001")).thenReturn(mockCourses);

        mockMvc.perform(get("/course/getCourseByCourseCode/{course_code}", "C001")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value(200))
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(jsonPath("$.data[0].name").value("Course 1"));
    }
}
