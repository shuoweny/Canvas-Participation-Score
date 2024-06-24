package com.swen90014.paplatypusbackend;

import com.itextpdf.text.DocumentException;
import com.swen90014.paplatypusbackend.controller.PdfController;
import com.swen90014.paplatypusbackend.dto.StudentPdfDTO;
import com.swen90014.paplatypusbackend.service.impl.PdfService;
import com.swen90014.paplatypusbackend.service.impl.ScoreServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PdfControllerTest {

    @Mock
    private PdfService pdfService;

    @Mock
    private ScoreServiceImpl scoreService;

    @InjectMocks
    private PdfController pdfController;

    private MockMvc mockMvc;
    private List<StudentPdfDTO> studentPdfDTOList;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(pdfController).build();
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
    public void testGetPdfByUserId_Success() throws Exception {
        Long courseId = 1L;
        Long userId = 2L;
        when(scoreService.getScorePdfForGivenUserId(userId, courseId)).thenReturn(studentPdfDTOList);

        byte[] pdfBytes = {1, 2, 3};
        when(pdfService.generatePdf(studentPdfDTOList, courseId, userId)).thenReturn(pdfBytes);

        mockMvc.perform(get("/pdf/download/{course_id}/{user_id}", courseId, userId)
                        .contentType(MediaType.APPLICATION_PDF))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_PDF))
                .andExpect(header().string("Content-Disposition", "inline; filename=data.pdf"))
                .andExpect(content().bytes(pdfBytes));
    }

    @Test
    public void testGetPdfByUserId_Fail() throws Exception {
        Long courseId = 1L;
        Long userId = 2L;

        when(scoreService.getScorePdfForGivenUserId(userId, courseId)).thenReturn(studentPdfDTOList);

        when(pdfService.generatePdf(studentPdfDTOList, courseId,userId)).thenThrow(DocumentException.class);

        mockMvc.perform(get("/pdf/download/{course_id}/{user_id}", courseId, userId)
                        .contentType(MediaType.APPLICATION_PDF))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.state").value(500))
                .andExpect(jsonPath("$.message").value("FAIL"));
    }
}

