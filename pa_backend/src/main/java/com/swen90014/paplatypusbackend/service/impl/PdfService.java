package com.swen90014.paplatypusbackend.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.swen90014.paplatypusbackend.dto.StudentPdfDTO;
import com.swen90014.paplatypusbackend.service.IChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class PdfService {

    private IChartService chartService;
    @Autowired
    public PdfService(IChartService chartService) {
        this.chartService = chartService;
    }

    public byte[] generatePdf(List<StudentPdfDTO> studentPdfDTOList, Long courseid, Long userid) throws DocumentException, IOException {
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();

        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Paragraph title = new Paragraph("Participation Report", font);
        document.add(title);
        document.add(Chunk.NEWLINE);
        document.add(new Paragraph("Hello, "+studentPdfDTOList.get(0).getStudentName()));
        document.add(new Paragraph("This is your participation report in the recent period"));
        document.add(Chunk.NEWLINE);


        BufferedImage chart1 = chartService.studentChart(courseid, userid);
        if (chart1 != null){
            document.add(new Paragraph("Your Personal Participation Pie Chart"));
            document.add(Chunk.NEWLINE);
            Image chartImage1 = Image.getInstance(chart1, null);
            chartImage1.scaleToFit(500, 250);
            document.add(chartImage1);
            document.add(Chunk.NEWLINE);
        }



        BufferedImage chart2 = chartService.subjectChart(courseid);
        if (chart2!=null){
            document.add(new Paragraph("The Participation of all student Bar Chart"));
            document.add(Chunk.NEWLINE);
            Image chartImage2 = Image.getInstance(chart2, null);
            chartImage2.scaleToFit(500, 300);
            document.add(chartImage2);
            document.add(Chunk.NEWLINE);
        }



        PdfPTable table = new PdfPTable(6);
        table.addCell("Course Name");
        table.addCell("Assignment Name");
        table.addCell("Type");
        table.addCell("Score");
        table.addCell("Participation Score Weight");
        table.addCell("Participation Score Complete");

        for (StudentPdfDTO data : studentPdfDTOList) {
            table.addCell(data.getCourseName() != null ? data.getCourseName() : "N/A");
            table.addCell(data.getAssignmentName() != null ? data.getAssignmentName() : "N/A");
            table.addCell(data.getType() != null ? data.getType() : "N/A");
            table.addCell(data.getScore() != null ? data.getScore().toString() : "N/A");
            table.addCell(data.getParticipationScoreWeight() != null ? data.getParticipationScoreWeight().toString() : "N/A");
            table.addCell(data.getParticipationScoreComplete() != null ? data.getParticipationScoreComplete().toString() : "N/A");
        }

        document.add(table);
        document.close();
        return baos.toByteArray();
    }

    public Path saveGeneratePdf(List<StudentPdfDTO> studentPdfDTOList,Long courseid, Long userid) throws DocumentException, IOException {
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();

        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Paragraph title = new Paragraph("Participation Report", font);
        document.add(title);
        document.add(Chunk.NEWLINE);
        document.add(new Paragraph("Hello, "+studentPdfDTOList.get(0).getStudentName()));
        document.add(new Paragraph("This is your participation report in the recent period"));
        document.add(Chunk.NEWLINE);

        BufferedImage chart = chartService.studentChart(courseid, userid);
        Image chartImage = Image.getInstance(chart, null);
        chartImage.scaleToFit(500, 300);
        document.add(chartImage);


        document.add(Chunk.NEWLINE);
        PdfPTable table = new PdfPTable(6);
        table.addCell("Course Name");
        table.addCell("Assignment Name");
        table.addCell("Type");
        table.addCell("Score");
        table.addCell("Participation Score Weight");
        table.addCell("Participation Score Complete");

        for (StudentPdfDTO data : studentPdfDTOList) {
            table.addCell(data.getCourseName() != null ? data.getCourseName() : "N/A");
            table.addCell(data.getAssignmentName() != null ? data.getAssignmentName() : "N/A");
            table.addCell(data.getType() != null ? data.getType() : "N/A");
            table.addCell(data.getScore() != null ? data.getScore().toString() : "N/A");
            table.addCell(data.getParticipationScoreWeight() != null ? data.getParticipationScoreWeight().toString() : "N/A");
            table.addCell(data.getParticipationScoreComplete() != null ? data.getParticipationScoreComplete().toString() : "N/A");
        }

        document.add(table);
        document.close();
        try {
            Path tempFilePath = Files.createTempFile("report_", ".pdf");
            Files.write(tempFilePath, baos.toByteArray());

            return tempFilePath;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
