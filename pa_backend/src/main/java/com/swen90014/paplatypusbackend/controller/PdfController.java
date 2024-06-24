package com.swen90014.paplatypusbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.DocumentException;
import com.swen90014.paplatypusbackend.controller.utils.ResultUtil;
import com.swen90014.paplatypusbackend.dto.StudentPdfDTO;
import com.swen90014.paplatypusbackend.service.impl.PdfService;
import com.swen90014.paplatypusbackend.service.impl.ScoreServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/pdf")
public class PdfController {

    @Autowired
    private PdfService pdfService;
    @Autowired

    private ScoreServiceImpl scoreService;

    @GetMapping(value = "/download/{course_id}/{user_id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public void getPdfByUserId(@PathVariable("course_id") Long courseId, @PathVariable("user_id") Long userId, HttpServletResponse response){
        List<StudentPdfDTO> studentPdfDTOList = scoreService.getScorePdfForGivenUserId(userId, courseId);
        try {
            byte[] bytes = pdfService.generatePdf(studentPdfDTOList, courseId, userId);
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=data.pdf");
            response.getOutputStream().write(bytes);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            // Set response to error
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            ResultUtil result = new ResultUtil(500, null, "FAIL");
            try {
                String json = new ObjectMapper().writeValueAsString(result);
                response.getWriter().write(json);
            } catch (IOException jsonException) {
                jsonException.printStackTrace();
            }
        }
    }
}

