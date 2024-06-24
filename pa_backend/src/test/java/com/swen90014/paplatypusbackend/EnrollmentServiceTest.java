package com.swen90014.paplatypusbackend;

import com.itextpdf.text.DocumentException;
import com.swen90014.paplatypusbackend.dao.EnrollmentDao;
import com.swen90014.paplatypusbackend.domain.Enrollment;
import com.swen90014.paplatypusbackend.dto.StudentPdfDTO;
import com.swen90014.paplatypusbackend.service.impl.*;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EnrollmentServiceTest {

    @InjectMocks
    private EnrollmentServiceImpl enrollmentService;

    @Mock
    private EnrollmentDao enrollmentDao;
    private List<Enrollment> enrollments;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        enrollments = new ArrayList<>();
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setId(1L);
        enrollment1.setType("Type1");
        enrollment1.setUserId(101L);
        enrollment1.setCourseId(201L);
        enrollment1.setEnrollmentState("Enrolled");

        Enrollment enrollment2 = new Enrollment();
        enrollment2.setId(2L);
        enrollment2.setType("Type2");
        enrollment2.setUserId(102L);
        enrollment2.setCourseId(202L);
        enrollment2.setEnrollmentState("Enrolled");

        Enrollment enrollment3 = new Enrollment();
        enrollment3.setId(3L);
        enrollment3.setType("Type3");
        enrollment3.setUserId(103L);
        enrollment3.setCourseId(203L);
        enrollment3.setEnrollmentState("Dropped");
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);
    }

    @Test
    void testSaveAllEnrollment() {

        doNothing().when(enrollmentDao).insertOrUpdateBatch(enrollments);

        enrollmentService.saveAllEnrollment(enrollments);

        verify(enrollmentDao, times(1)).insertOrUpdateBatch(enrollments);
    }

    @Test
    void testGetEnrollmentByCourseId() {
        Long courseId = 1L;
        List<Enrollment> enrollments = new ArrayList<>();
        when(enrollmentDao.getEnrollmentByCourseId(courseId)).thenReturn(enrollments);

        List<Enrollment> result = enrollmentService.getEnrollmentByCourseId(courseId);

        assertEquals(enrollments, result);
    }

    @Test
    void testGetEnrollmentByUserId() {
        Long userId = 1L;
        List<Enrollment> enrollments = new ArrayList<>();
        when(enrollmentDao.getEnrollmentByUserId(userId)).thenReturn(enrollments);

        List<Enrollment> result = enrollmentService.getEnrollmentByUserId(userId);

        assertEquals(enrollments, result);
    }
}