package com.swen90014.paplatypusbackend;

import com.swen90014.paplatypusbackend.controller.StudentController;
import com.swen90014.paplatypusbackend.domain.Student;
import com.swen90014.paplatypusbackend.service.IStudentService;
import com.swen90014.paplatypusbackend.controller.utils.ResultUtil;
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

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

public class StudentControllerTest {
    private MockMvc mockMvc;

    @Mock
    private IStudentService iStudentService;

    @InjectMocks
    private StudentController studentController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }

    @Test
    public void testGetAllStudents() throws Exception {
        List<Student> studentList = Arrays.asList(new Student(1L, 1L, "John Doe", "12345", "johndoe", "Student", 1L, "johndoe@example.com"));

        when(iStudentService.list()).thenReturn(studentList);

        mockMvc.perform(MockMvcRequestBuilders.get("/student/getAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.state").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].studentId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("SUCCESS"));
    }

    @Test
    public void testGetStudentById() throws Exception {
        Long studentId = 1L;
        Student student = new Student(1L, 1L, "John Doe", "12345", "johndoe", "Student", 1L, "johndoe@example.com");

        when(iStudentService.getById(studentId)).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders.get("/student/getStudentById/{id}", studentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.state").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.studentId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("SUCCESS"));
    }

    @Test
    public void testGetStudentByStudentName() throws Exception {
        String studentName = "John Doe";
        List<Student> studentList = Arrays.asList(new Student(1L, 1L, "John Doe", "12345", "johndoe", "Student", 1L, "johndoe@example.com"));

        when(iStudentService.getStudentByStudentName(studentName)).thenReturn(studentList);

        mockMvc.perform(MockMvcRequestBuilders.get("/student/getStudentByStudentName/{name}", studentName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.state").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].name").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("SUCCESS"));
    }

    @Test
    public void testGetStudentByStudentNumber() throws Exception {
        String studentNumber = "12345";
        List<Student> studentList = Arrays.asList(new Student(1L, 1L, "John Doe", "12345", "johndoe", "Student", 1L, "johndoe@example.com"));

        when(iStudentService.getStudentByStudentNumber(studentNumber)).thenReturn(studentList);

        mockMvc.perform(MockMvcRequestBuilders.get("/student/getStudentByStudentNumber/{sis_user_id}", studentNumber)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.state").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].sis_user_id").value("12345"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("SUCCESS"));
    }

    @Test
    public void testGetStudentByUserLogin() throws Exception {
        String userLogin = "johndoe";
        List<Student> studentList = Arrays.asList(new Student(1L, 1L, "John Doe", "12345", "johndoe", "Student", 1L, "johndoe@example.com"));

        when(iStudentService.getStudentByUserLogin(userLogin)).thenReturn(studentList);

        mockMvc.perform(MockMvcRequestBuilders.get("/student/getStudentByUserLogin/{login_id}", userLogin)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.state").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].login_id").value("johndoe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("SUCCESS"));
    }
}