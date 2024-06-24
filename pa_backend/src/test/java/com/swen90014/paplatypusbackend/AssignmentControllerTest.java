package com.swen90014.paplatypusbackend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swen90014.paplatypusbackend.controller.AssignmentController;
import com.swen90014.paplatypusbackend.domain.Assignment;
import com.swen90014.paplatypusbackend.service.impl.AssignmentServiceImpl;
import com.swen90014.paplatypusbackend.service.impl.ScoreServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AssignmentControllerTest {

    @InjectMocks
    private AssignmentController assignmentController;

    @Mock
    private AssignmentServiceImpl assignmentService;

    @Mock
    private ScoreServiceImpl scoreService;

    private MockMvc mockMvc;
    private List<Map<String, Object>> weights;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(assignmentController).build();
        weights = Arrays.asList(
                new HashMap<String, Object>() {{
                    put("mode", "group");
                    put("type", "tutorial");
                    put("weight", 15);
                    put("method", "complete");
                }},
                new HashMap<String, Object>() {{
                    put("mode", "group");
                    put("type", "lecture");
                    put("weight", 60);
                    put("method", "weight");
                }},
                new HashMap<String, Object>() {{
                    put("mode", "group");
                    put("type", "h5p");
                    put("weight", 30);
                    put("method", "complete");
                }},
                new HashMap<String, Object>() {{
                    put("mode", "group");
                    put("type", "canvas");
                    put("weight", 40);
                    put("method", "complete");
                }},
                new HashMap<String, Object>() {{
                    put("mode", "single");
                    put("assignmentId", 45410);
                    put("weight", 20);
                    put("method", "complete");
                }}
        );
    }

    @Test
    void testGetAll() throws Exception {
        List<Assignment> mockAssignments = new ArrayList<>();
        Assignment assignment1 = new Assignment();
        assignment1.setId(1L);
        assignment1.setName("Assignment 1");
        mockAssignments.add(assignment1);

        when(assignmentService.list()).thenReturn(mockAssignments);

        mockMvc.perform(get("/assignment/getAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value(200))
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(jsonPath("$.data[0].name").value("Assignment 1"));
    }

    @Test
    void testGetById() throws Exception {
        Assignment mockAssignment = new Assignment();
        mockAssignment.setId(1L);
        mockAssignment.setName("Assignment 1");

        when(assignmentService.getById(1L)).thenReturn(mockAssignment);

        mockMvc.perform(get("/assignment/getAssignmentById/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value(200))
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(jsonPath("$.data.name").value("Assignment 1"));
    }

    @Test
    void testGetAssignmentByCourseId() throws Exception {
        List<Assignment> mockAssignments = new ArrayList<>();
        Assignment assignment1 = new Assignment();
        assignment1.setId(1L);
        assignment1.setName("Assignment 1");
        mockAssignments.add(assignment1);

        when(assignmentService.getAssignmentByCourseId(1L)).thenReturn(mockAssignments);

        mockMvc.perform(get("/assignment/getAssignmentByCourseId/{course_id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value(200))
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(jsonPath("$.data[0].name").value("Assignment 1"));
    }

    @Test
    void testChangeWeight() throws Exception {

        doNothing().when(assignmentService).updateWeights(1L, weights);
        ObjectMapper objectMapper = new ObjectMapper();
        String request = "{\"weights\": " + objectMapper.writeValueAsString(weights) + "}";
        mockMvc.perform(post("/assignment/changeWeight/{course_id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value(200))
                .andExpect(jsonPath("$.message").value("SUCCESS"));

        verify(assignmentService, times(1)).updateWeights(1L, weights);
        verify(scoreService, times(1)).setParticipationScoreByScore(1L);
    }
}