package com.swen90014.paplatypusbackend;

import com.swen90014.paplatypusbackend.dao.CourseDao;
import com.swen90014.paplatypusbackend.domain.*;
import com.swen90014.paplatypusbackend.service.impl.CanvasService;
import com.swen90014.paplatypusbackend.service.impl.CourseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CourseServiceTest {
    @Mock
    private CourseDao courseDao;

    @InjectMocks
    private CourseServiceImpl courseService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testSaveAllCourses() {
        List<Course> courses = new ArrayList<>();

        doNothing().when(courseDao).insertOrUpdateBatch(courses);

        courseService.saveAllCourses(courses);

        verify(courseDao, times(1)).insertOrUpdateBatch(courses);
    }

    @Test
    public void testGetCourseByCourseName() {
        String courseName = "Test Course";
        List<Course> expectedCourses = new ArrayList<>();

        when(courseDao.getCourseByCourseName(courseName)).thenReturn(expectedCourses);

        List<Course> result = courseService.getCourseByCourseName(courseName);

        assertNotNull(result);
        assertEquals(expectedCourses, result);
    }

    @Test
    public void testGetCourseByCourseCode() {
        String courseCode = "CS101";
        List<Course> expectedCourses = new ArrayList<>();

        when(courseDao.getCourseByCourseCode(courseCode)).thenReturn(expectedCourses);

        List<Course> result = courseService.getCourseByCourseCode(courseCode);

        assertNotNull(result);
        assertEquals(expectedCourses, result);
    }

    @Test
    public void testSelectAllIds() {
        List<Long> expectedIds = new ArrayList<>();

        when(courseDao.selectAllIds()).thenReturn(expectedIds);

        List<Long> result = courseService.selectAllIds();

        assertNotNull(result);
        assertEquals(expectedIds, result);
    }

    @Test
    public void testSelectCourseByUserId() {
        Long userId = 1L;
        List<Course> expectedCourses = new ArrayList<>();

        when(courseDao.selectCourseByUserId(userId)).thenReturn(expectedCourses);

        List<Course> result = courseService.selectCourseByUserId(userId);

        assertNotNull(result);
        assertEquals(expectedCourses, result);
    }
}
