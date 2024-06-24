package com.swen90014.paplatypusbackend;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.swen90014.paplatypusbackend.dto.StudentPdfDTO;
import com.swen90014.paplatypusbackend.service.impl.ChartServiceImpl;
import com.swen90014.paplatypusbackend.service.impl.PdfService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PdfServiceTest {

    @InjectMocks
    private PdfService pdfService;

    @Mock
    private PdfWriter pdfWriter;

    @Mock
    private ChartServiceImpl chartService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pdfService = new PdfService(chartService);
    }


    @Test
    public void testGeneratePdf() throws DocumentException, IOException {
        Long courseId = 1L;
        Long userId = 1L;
        StudentPdfDTO studentPdfDTO = new StudentPdfDTO();
        studentPdfDTO.setStudentName("John Doe");
        List<StudentPdfDTO> studentPdfDTOList = Arrays.asList(studentPdfDTO);

        when(chartService.studentChart(anyLong(), anyLong())).thenReturn(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB));
        when(chartService.subjectChart(anyLong())).thenReturn(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB));

        byte[] result = pdfService.generatePdf(studentPdfDTOList, courseId, userId);

        assertNotNull(result);
        assertTrue(result.length > 0);
    }

    @Test
    public void testSaveGeneratePdf() throws DocumentException, IOException {
        Long courseId = 1L;
        Long userId = 1L;
        StudentPdfDTO studentPdfDTO = new StudentPdfDTO();
        studentPdfDTO.setStudentName("John Doe");
        List<StudentPdfDTO> studentPdfDTOList = Arrays.asList(studentPdfDTO);
        when(chartService.studentChart(anyLong(), anyLong())).thenReturn(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB));
        Path result = pdfService.saveGeneratePdf(studentPdfDTOList, courseId, userId);
        assertNotNull(result);
    }
}

