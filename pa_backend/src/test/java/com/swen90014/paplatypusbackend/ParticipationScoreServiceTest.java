package com.swen90014.paplatypusbackend;

import com.swen90014.paplatypusbackend.dao.ScoreDao;
import com.swen90014.paplatypusbackend.dto.ExcelDTO;
import com.swen90014.paplatypusbackend.service.impl.ParticipationScoreServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ParticipationScoreServiceTest {

    @InjectMocks
    private ParticipationScoreServiceImpl participationScoreService;

    @Mock
    private ScoreDao scoreDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCalculateAndSaveByComplete() {
        Long courseId = 1L;

        doNothing().when(scoreDao).setParticipationScoreByComplete(courseId);

        participationScoreService.CalculateAndSaveByComplete(courseId);

        verify(scoreDao, times(1)).setParticipationScoreByComplete(courseId);
    }

    @Test
    void testCalculateAndSaveByScore() {
        Long courseId = 1L;

        doNothing().when(scoreDao).setParticipationScoreByScore(courseId);

        participationScoreService.CalculateAndSaveByScore(courseId);

        verify(scoreDao, times(1)).setParticipationScoreByScore(courseId);
    }

    @Test
    void testGetExcelDTO() {
        Long courseId = 1L;
        ExcelDTO excelDTO1 = new ExcelDTO();
        excelDTO1.setStudentName("John Doe");
        excelDTO1.setUserId(101);
        excelDTO1.setAssignmentName("Assignment 1");

        ExcelDTO excelDTO2 = new ExcelDTO();
        excelDTO2.setStudentName("Jane Smith");
        excelDTO2.setUserId(102);
        excelDTO2.setAssignmentName("Assignment 2");

        List<ExcelDTO> mockExcelDTOList = new ArrayList<>();
        mockExcelDTOList.add(excelDTO1);
        mockExcelDTOList.add(excelDTO2);
        when(scoreDao.getExcelDTO(courseId)).thenReturn(mockExcelDTOList);

        List<ExcelDTO> result = participationScoreService.getExcelDTO(courseId);

        verify(scoreDao, times(1)).getExcelDTO(courseId);

        assertEquals(mockExcelDTOList, result);
    }
}
