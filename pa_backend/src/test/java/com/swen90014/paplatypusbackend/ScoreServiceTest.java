package com.swen90014.paplatypusbackend;

import com.swen90014.paplatypusbackend.dao.ScoreDao;
import com.swen90014.paplatypusbackend.domain.Score;
import com.swen90014.paplatypusbackend.dto.ParticipationWeightDTO;
import com.swen90014.paplatypusbackend.dto.StudentPdfDTO;
import com.swen90014.paplatypusbackend.service.impl.ScoreServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ScoreServiceTest {

    @InjectMocks
    private ScoreServiceImpl scoreService;

    @Mock
    private ScoreDao scoreDao;

    private List<ParticipationWeightDTO> participationWeightDTOList;
    private List<StudentPdfDTO> studentPdfDTOList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        participationWeightDTOList = new ArrayList<>();

        ParticipationWeightDTO weightDTO1 = new ParticipationWeightDTO();
        weightDTO1.setId(1L);
        weightDTO1.setType("Type1");
        weightDTO1.setParticipationScoreWeight(95.0);
        weightDTO1.setParticipationScoreComplete(85.0);
        weightDTO1.setAssignmentId(101L);
        weightDTO1.setName("John Doe");
        weightDTO1.setUserId(201L);
        weightDTO1.setScore(92.0);

        ParticipationWeightDTO weightDTO2 = new ParticipationWeightDTO();
        weightDTO2.setId(2L);
        weightDTO2.setType("Type2");
        weightDTO2.setParticipationScoreWeight(90.0);
        weightDTO2.setParticipationScoreComplete(80.0);
        weightDTO2.setAssignmentId(102L);
        weightDTO2.setName("Jane Smith");
        weightDTO2.setUserId(202L);
        weightDTO2.setScore(88.0);

        participationWeightDTOList.add(weightDTO1);
        participationWeightDTOList.add(weightDTO2);

        studentPdfDTOList = new ArrayList<>();

        StudentPdfDTO pdfDTO1 = new StudentPdfDTO();
        pdfDTO1.setType("Type1");
        pdfDTO1.setParticipationScoreWeight(95.0);
        pdfDTO1.setParticipationScoreComplete(85.0);
        pdfDTO1.setAssignmentName("Assignment 1");
        pdfDTO1.setCourseName("Course 101");
        pdfDTO1.setStudentName("John Doe");
        pdfDTO1.setScore(92.0);
        pdfDTO1.setEmail("john.doe@example.com");

        StudentPdfDTO pdfDTO2 = new StudentPdfDTO();
        pdfDTO2.setType("Type2");
        pdfDTO2.setParticipationScoreWeight(90.0);
        pdfDTO2.setParticipationScoreComplete(80.0);
        pdfDTO2.setAssignmentName("Assignment 2");
        pdfDTO2.setCourseName("Course 102");
        pdfDTO2.setStudentName("Jane Smith");
        pdfDTO2.setScore(88.0);
        pdfDTO2.setEmail("jane.smith@example.com");

        studentPdfDTOList.add(pdfDTO1);
        studentPdfDTOList.add(pdfDTO2);
    }

    @Test
    void testSaveAllScore() {
        List<Score> scores = new ArrayList<>();
        Score score1 = new Score();
        score1.setId(1L);
        scores.add(score1);

        Score score2 = new Score();
        score2.setId(2L);
        scores.add(score2);

        doNothing().when(scoreDao).insertOrUpdateBatch(scores);

        scoreService.saveAllScore(scores);

        verify(scoreDao, times(1)).insertOrUpdateBatch(scores);
    }

    @Test
    void testGetScoreForGivenAssignmentId() {
        Long assignmentId = 1L;
        Long courseId = 2L;

        when(scoreDao.getScoreForGivenAssignmentId(assignmentId, courseId)).thenReturn(participationWeightDTOList);

        List<ParticipationWeightDTO> result = scoreService.getScoreForGivenAssignmentId(assignmentId, courseId);

        assertEquals(participationWeightDTOList, result);
    }

    @Test
    void testGetScoreForGivenUserId() {
        Long userId = 1L;
        Long courseId = 2L;

        when(scoreDao.getScoreForGivenUserId(userId, courseId)).thenReturn(participationWeightDTOList);

        List<ParticipationWeightDTO> result = scoreService.getScoreForGivenUserId(userId, courseId);

        assertEquals(participationWeightDTOList, result);
    }

    @Test
    void testGetOneScoreForGivenAssignmentId() {
        Long userId = 1L;
        Long assignmentId = 2L;
        Long courseId = 3L;

        when(scoreDao.getOneScoreForGivenAssignmentId(userId, assignmentId, courseId)).thenReturn(participationWeightDTOList);

        List<ParticipationWeightDTO> result = scoreService.getOneScoreForGivenAssignmentId(userId, assignmentId, courseId);

        assertEquals(participationWeightDTOList, result);
    }


    @Test
    void testSetParticipationScoreByComplete() {
        Long courseId = 1L;

        doNothing().when(scoreDao).setParticipationScoreByComplete(courseId);

        scoreService.setParticipationScoreByComplete(courseId);

        verify(scoreDao, times(1)).setParticipationScoreByComplete(courseId);
    }

    @Test
    void testSetParticipationScoreByScore() {
        Long courseId = 1L;

        doNothing().when(scoreDao).setParticipationScoreByScore(courseId);

        scoreService.setParticipationScoreByScore(courseId);

        verify(scoreDao, times(1)).setParticipationScoreByScore(courseId);
    }

    @Test
    void testGetScorePdfForGivenUserId() {
        Long userId = 1L;
        Long courseId = 2L;

        when(scoreDao.getScorePdfForGivenUserId(userId, courseId)).thenReturn(studentPdfDTOList);

        List<StudentPdfDTO> result = scoreService.getScorePdfForGivenUserId(userId, courseId);

        assertEquals(studentPdfDTOList, result);
    }
}
