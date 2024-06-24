package com.swen90014.paplatypusbackend.service.impl;

import com.itextpdf.text.DocumentException;
import com.swen90014.paplatypusbackend.dto.StudentPdfDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class EmailService {
    private final JavaMailSender emailSender;
    private final StudentServiceImpl studentService;
    private final ScoreServiceImpl scoreService;
    private final PdfService pdfService;
    @Autowired
    public EmailService(JavaMailSender emailSender, StudentServiceImpl studentService, ScoreServiceImpl scoreService, PdfService pdfService) {
        this.emailSender = emailSender;
        this.studentService = studentService;
        this.scoreService = scoreService;
        this.pdfService = pdfService;
    }

    public void sendEmailWithAttachment(Path filePath, String email) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setSubject("Your Participation Report");
        helper.setText("Please find attached your participation report.");
        helper.setTo(email);

        FileSystemResource file = new FileSystemResource(filePath.toFile());
        helper.addAttachment("participation_report.pdf", file);

        emailSender.send(message);
        try {
            Files.delete(filePath);
        } catch (IOException e) {
            System.err.println("Failed to delete file: " + e.getMessage());
        }
    }
    @Async
    @Retryable(value = {DocumentException.class, IOException.class, MessagingException.class, RuntimeException.class}, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public CompletableFuture<Boolean> sendToAllStudent(Long course_id) {
        List<Long> idList = studentService.getStudentIdByCourseId(course_id);
        for (Long id : idList) {
            List<StudentPdfDTO> studentPdfDTOList = scoreService.getScorePdfForGivenUserId(id, course_id);
            Path path = null;
            try {
                path = pdfService.saveGeneratePdf(studentPdfDTOList, course_id, id);
            } catch (DocumentException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }  catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            try {
                sendEmailWithAttachment(path, studentPdfDTOList.get(0).getEmail());
            } catch (MessagingException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return CompletableFuture.completedFuture(true);
    }

    @Async
    @Retryable(value = {DocumentException.class, IOException.class, MessagingException.class, RuntimeException.class}, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public CompletableFuture<Boolean> sendToIndividualStudent(Long course_id, Long user_id){
        try {
            List<StudentPdfDTO> studentPdfDTOList = scoreService.getScorePdfForGivenUserId(user_id, course_id);
            Path path = null;
            try {
                path = pdfService.saveGeneratePdf(studentPdfDTOList, course_id, user_id);
            } catch (DocumentException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            sendEmailWithAttachment(path, studentPdfDTOList.get(0).getEmail());
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return CompletableFuture.completedFuture(true);
    }

}
