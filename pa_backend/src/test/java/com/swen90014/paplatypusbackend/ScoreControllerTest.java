package com.swen90014.paplatypusbackend;


import com.swen90014.paplatypusbackend.controller.ScoreController;
import com.swen90014.paplatypusbackend.domain.Score;
import com.swen90014.paplatypusbackend.dto.ParticipationWeightDTO;
import com.swen90014.paplatypusbackend.service.IScoreService;
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

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

public class ScoreControllerTest {
    private MockMvc mockMvc;

    @Mock
    private IScoreService iScoreService;

    @InjectMocks
    private ScoreController scoreController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(scoreController).build();
    }

    @Test
    public void testGetAllScores() throws Exception {
        List<Score> scoreList = Arrays.asList(new Score(1L, 90.0, 1, 1L, new Timestamp(System.currentTimeMillis()), false, false, false, 90.0, 0.0, 1L, 0.0));

        when(iScoreService.list()).thenReturn(scoreList);

        mockMvc.perform(MockMvcRequestBuilders.get("/score/getAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.state").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("SUCCESS"));
    }

    @Test
    public void testGetScoreByScoreId() throws Exception {
        Long scoreId = 1L;
        Score score = new Score(1L, 90.0, 1, 1L, new Timestamp(System.currentTimeMillis()), false, false, false, 90.0, 0.0, 1L, 0.0);

        when(iScoreService.getById(scoreId)).thenReturn(score);

        mockMvc.perform(MockMvcRequestBuilders.get("/score/getByScoreId/{id}", scoreId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.state").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("SUCCESS"));
    }

    @Test
    public void testGetScoreForGivenUserId() throws Exception {
        Long courseId = 1L;
        Long userId = 2L;
        List<ParticipationWeightDTO> scoreList = Arrays.asList(new ParticipationWeightDTO(1L, "type", 90.0, 0.0, 1L, "name", 1L, 90.0, "complete"));

        when(iScoreService.getScoreForGivenUserId(userId, courseId)).thenReturn(scoreList);

        mockMvc.perform(MockMvcRequestBuilders.get("/score/getScoreForGivenUserId/{course_id}/{user_id}", courseId, userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.state").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].userId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("SUCCESS"));
    }

    @Test
    public void testGetScoreForGivenAssignmentId() throws Exception {
        Long courseId = 1L;
        Long assignmentId = 2L;
        List<ParticipationWeightDTO> scoreList = Arrays.asList(new ParticipationWeightDTO(1L, "type", 90.0, 0.0, 1L, "name", 1L, 90.0, "weight"));

        when(iScoreService.getScoreForGivenAssignmentId(assignmentId, courseId)).thenReturn(scoreList);

        mockMvc.perform(MockMvcRequestBuilders.get("/score/getScoreForGivenAssignmentId/{course_id}/{assignment_id}", courseId, assignmentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.state").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].userId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("SUCCESS"));
    }

    @Test
    public void testGetOneScoreForGivenAssignmentId() throws Exception {
        Long courseId = 1L;
        Long assignmentId = 2L;
        Long userId = 3L;
        List<ParticipationWeightDTO> scoreList = Arrays.asList(new ParticipationWeightDTO(1L, "type", 90.0, 0.0, 1L, "name", 1L, 90.0, "complete"));

        when(iScoreService.getOneScoreForGivenAssignmentId(userId, assignmentId, courseId)).thenReturn(scoreList);

        mockMvc.perform(MockMvcRequestBuilders.get("/score/getOneScoreForGivenAssignmentId/{course_id}/{assignment_id}/{user_id}", courseId, assignmentId, userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.state").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].userId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("SUCCESS"));
    }

}
