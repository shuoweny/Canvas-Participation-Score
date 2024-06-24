package com.swen90014.paplatypusbackend;

import com.swen90014.paplatypusbackend.dao.AssignmentDao;
import com.swen90014.paplatypusbackend.domain.Assignment;
import com.swen90014.paplatypusbackend.domain.Course;
import com.swen90014.paplatypusbackend.service.impl.AssignmentServiceImpl;
import com.swen90014.paplatypusbackend.service.impl.CourseServiceImpl;
import com.swen90014.paplatypusbackend.service.impl.ScoreServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AssignmentServiceTest {

    @InjectMocks
    private AssignmentServiceImpl assignmentService;

    @Mock
    private AssignmentDao assignmentDao;

    private List<Map<String, Object>> weights;

    @Mock
    private ScoreServiceImpl scoreService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        weights = Arrays.asList(
                new HashMap<String, Object>() {{
                    put("mode", "group");
                    put("type", "tutorial");
                    put("weight", 15);
                    put("method", "complete");
                }},
                new HashMap<String, Object>() {{
                    put("mode", "group");
                    put("type", "lecture");
                    put("weight", 60);
                    put("method", "weight");
                }},
                new HashMap<String, Object>() {{
                    put("mode", "group");
                    put("type", "h5p");
                    put("weight", 30);
                    put("method", "complete");
                }},
                new HashMap<String, Object>() {{
                    put("mode", "group");
                    put("type", "canvas");
                    put("weight", 40);
                    put("method", "complete");
                }},
                new HashMap<String, Object>() {{
                    put("mode", "single");
                    put("assignmentId", 45410);
                    put("weight", 20);
                    put("method", "complete");
                }}
        );
    }

    @Test
    void testSaveAllAssignment() {
        Timestamp timestamp = Timestamp.valueOf("2023-01-01 00:00:00");
        BigDecimal pointsPossible = new BigDecimal("100.0");

        Assignment assignment1 = new Assignment(
                1L,
                "description1",
                timestamp,
                timestamp,
                timestamp,
                pointsPossible,
                timestamp,
                timestamp,
                true,
                1L,
                "name1",
                10,
                "type1",
                20,
                "complete",
                "complete"
        );

        Assignment assignment2 = new Assignment(
                2L,
                "description2",
                timestamp,
                timestamp,
                timestamp,
                pointsPossible,
                timestamp,
                timestamp,
                true,
                2L,
                "name2",
                15,
                "type2",
                25,
                "weight",
                "weight"
        );
        List<Assignment> assignments = Arrays.asList(assignment1, assignment2);
        doNothing().when(assignmentDao).insertOrUpdateBatch(assignments);
        assignmentService.saveAllAssignment(assignments);
        verify(assignmentDao, times(1)).insertOrUpdateBatch(assignments);
    }

    @Test
    void testGetAssignmentByCourseId() {
        Long courseId = 1L;
        Timestamp timestamp = Timestamp.valueOf("2023-01-01 00:00:00");
        BigDecimal pointsPossible = new BigDecimal("100.0");

        Assignment assignment1 = new Assignment(
                1L,
                "description1",
                timestamp,
                timestamp,
                timestamp,
                pointsPossible,
                timestamp,
                timestamp,
                true,
                1L,
                "name1",
                10,
                "type1",
                20,
                "complete",
                "weight"
        );

        Assignment assignment2 = new Assignment(
                2L,
                "description2",
                timestamp,
                timestamp,
                timestamp,
                pointsPossible,
                timestamp,
                timestamp,
                true,
                2L,
                "name2",
                15,
                "type2",
                25,
                "weight",
                "complete"
        );
        List<Assignment> expectedAssignments = Arrays.asList(assignment1, assignment2);
        when(assignmentDao.getAssignmentByCourseId(courseId)).thenReturn(expectedAssignments);
        List<Assignment> result = assignmentService.getAssignmentByCourseId(courseId);
        assertEquals(expectedAssignments, result);
    }

    @Test
    void testUpdateWeights() {
        Long courseId = 1L;
        doNothing().when(assignmentDao).updateWeightByTypeAndCourseId(anyString(), anyInt(), anyLong(), anyString());
        assignmentService.updateWeights(courseId, weights);
        verify(assignmentDao, times(1)).updateWeightByTypeAndCourseId("h5p", 30, courseId, "complete");
        verify(assignmentDao, times(1)).updateWeightByTypeAndCourseId("canvas", 40, courseId, "complete");
    }

    @Test
    void testGetSortedAssignmentByCourseID() {
        Long courseId = 1L;
        List<String> expectedAssignments = Arrays.asList("Assignment1", "Assignment2");
        when(assignmentDao.getAssignmentByCourseIdSorted(courseId)).thenReturn(expectedAssignments);
        List<String> result = assignmentService.getSortedAssignmentByCourseID(courseId);
        assertEquals(expectedAssignments, result);
    }
}
