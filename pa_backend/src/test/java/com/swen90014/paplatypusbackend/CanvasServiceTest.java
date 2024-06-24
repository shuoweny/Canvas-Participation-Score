package com.swen90014.paplatypusbackend;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swen90014.paplatypusbackend.domain.*;
import com.swen90014.paplatypusbackend.service.impl.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.swen90014.paplatypusbackend.service.impl.CanvasService.DEFAULT_TUTORIAL_WEIGHT;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
public class CanvasServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private UserServiceImpl userService;
    @Mock
    private TeacherServiceImpl teacherService;
    @Mock
    private StudentServiceImpl studentService;
    @Mock
    private ScoreServiceImpl scoreService;
    @Mock
    private EnrollmentServiceImpl enrollmentService;
    @Mock
    private CourseServiceImpl courseService;
    @Mock
    private AssignmentServiceImpl assignmentService;

    @InjectMocks
    private CanvasService canvasService;

    public CanvasServiceTest() {
        MockitoAnnotations.openMocks(this);
    }
    private User testUser;
    private Course testCourse;
    private Enrollment testEnrollment;
    private Teacher testTeacher;
    private Student testStudent;
    private Assignment testAssignment;
    private Score testScore;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        canvasService = new CanvasService(restTemplate, userService, teacherService, studentService, scoreService, enrollmentService, courseService, assignmentService);
        testUser = new User(1L, "John Doe", "avatarUrl", "Doe", "John", "token");
        testCourse = new Course(1L, 1L, "Test Course", "COURSE101", 1L);
        testEnrollment = new Enrollment(1L, "type", 1L, 1L, "active");
        testTeacher = new Teacher(1L, 1L, "Jane Doe", "sisUserId", "loginId", "Instructor", 1L, "jane.doe@example.com", true);
        testStudent = new Student(1L, 1L, "Alice", "sisUserId", "loginId", "Student", 1L, "alice@example.com");
        testAssignment = new Assignment(1L, "description", Timestamp.valueOf("2023-09-01 10:10:10"), Timestamp.valueOf("2023-09-01 10:10:10"), Timestamp.valueOf("2023-09-01 10:10:10"), new BigDecimal(100), Timestamp.valueOf("2023-09-01 10:10:10"), Timestamp.valueOf("2023-09-01 10:10:10"), true, 1L, "name", 10, "type", 10, "complete", "weight");
        testScore = new Score(1L, 90.0, 1, 1L, Timestamp.valueOf("2023-09-01 10:10:10"), false, false, false, 90.0, 10.0, 1L, 90.0);

        doNothing().when(userService).insertOrUpdate(any(User.class));
        doNothing().when(courseService).saveAllCourses(anyList());
        doNothing().when(enrollmentService).saveAllEnrollment(anyList());
        doNothing().when(teacherService).saveAllTeacher(anyList());
        doNothing().when(studentService).saveAllStudent(anyList());
        doNothing().when(assignmentService).saveAllAssignment(anyList());
        doNothing().when(scoreService).saveAllScore(anyList());
        Map<Integer, String> TEACHER_ROLE_MAP = new HashMap<>();
        TEACHER_ROLE_MAP.put(1, "Instructor");
        canvasService.setTeacherRoleMap(TEACHER_ROLE_MAP);
    }
    @Test
    void testCallCanvasApiForCourses() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + "14228~zKW30HQ3x9om5L3z6zV9JEBL5Hvp8KFUHNg7l5rTHvSTwjUgwgpdzpV814xlOMC8");
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        Course course1 = new Course(1L, 1L, "Test Course", "COURSE101", 1L); // Set up course1
        ResponseEntity<Course[]> responseEntity = new ResponseEntity<>(new Course[]{course1}, HttpStatus.OK);

        when(restTemplate.exchange(
                eq("null/courses?per_page=50&page=1"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(Course[].class)))
                .thenReturn(responseEntity);
        List<Course> result = canvasService.callCanvasApiForCourses(testUser);
        verify(restTemplate, times(1)).exchange(
                eq("null/courses?per_page=50&page=1"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(Course[].class));

        assertNotNull(result);
        assertEquals(1, result.size());
    }
    @Test
    void testCallCanvasApiForEnrollment() {
        JsonNode courseNode1 = new ObjectMapper().createObjectNode()
                .put("id", 1)
                .set("enrollments", new ObjectMapper().createArrayNode()
                        .add(new ObjectMapper().createObjectNode()
                                .put("user_id", 101)
                                .put("type", "student")
                                .put("enrollment_state", "active")
                        )
                );

        JsonNode courseNode2 = new ObjectMapper().createObjectNode()
                .put("id", 2)
                .set("enrollments", new ObjectMapper().createArrayNode()
                        .add(new ObjectMapper().createObjectNode()
                                .put("user_id", 102)
                                .put("type", "teacher")
                                .put("enrollment_state", "inactive")
                        )
                );

        List<JsonNode> responseList = List.of(courseNode1, courseNode2);

        ResponseEntity<JsonNode> responseEntity = new ResponseEntity<>(new ObjectMapper().createArrayNode().addAll(responseList), HttpStatus.OK);

        when(restTemplate.exchange(
                eq("null/courses?per_page=50&page=1"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(JsonNode.class)))
                .thenReturn(responseEntity);

        List<Enrollment> result = canvasService.callCanvasApiForEnrollment();

        verify(restTemplate, times(1)).exchange(
                eq("null/courses?per_page=50&page=1"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(JsonNode.class)
        );

        assertNotNull(result);
        assertEquals(2, result.size());

        Enrollment enrollment1 = result.get(0);
        assertNotNull(enrollment1);
        assertEquals(1L, enrollment1.getCourseId());
        assertEquals(101L, enrollment1.getUserId());
        assertEquals("student", enrollment1.getType());
        assertEquals("active", enrollment1.getEnrollmentState());

        Enrollment enrollment2 = result.get(1);
        assertNotNull(enrollment2);
        assertEquals(2L, enrollment2.getCourseId());
        assertEquals(102L, enrollment2.getUserId());
        assertEquals("teacher", enrollment2.getType());
        assertEquals("inactive", enrollment2.getEnrollmentState());
    }

    @Test
    void testCallCanvasApiForTeacherByCourseID() {
        Long courseId = 1L;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer yourAccessToken");
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        Teacher teacher1 = new Teacher(1L, 1L, "John Doe", "sisUserId", "loginId", "Instructor", 1L, "john.doe@example.com", true);
        Teacher teacher2 = new Teacher(2L, 2L, "Jane Smith", "sisUserId", "loginId", "Instructor", 1L, "jane.smith@example.com", true);
        Teacher[] teacherArray = {teacher1, teacher2};

        ResponseEntity<Teacher[]> responseEntity = new ResponseEntity<>(teacherArray, HttpStatus.OK);

        when(restTemplate.exchange(
                eq("null/courses/1/users?enrollment_type[]=teacher&enrollment_role_id=1&per_page=50&page=1"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(Teacher[].class)))
                .thenReturn(responseEntity);

        List<Teacher> teacherList = canvasService.callCanvasApiForTeacherByCourseID(courseId);

        assertNotNull(teacherList);
        assertEquals(2, teacherList.size());

        Teacher teacherResult1 = teacherList.get(0);
        assertEquals(1L, teacherResult1.getCourseId());
        assertEquals(1L, teacherResult1.getId());
        assertEquals("Instructor", teacherResult1.getType());

        Teacher teacherResult2 = teacherList.get(1);
        assertEquals(1L, teacherResult2.getCourseId());
        assertEquals(2L, teacherResult2.getId());
        assertEquals("Instructor", teacherResult2.getType());

        verify(restTemplate, times(1)).exchange(
                eq("null/courses/1/users?enrollment_type[]=teacher&enrollment_role_id=1&per_page=50&page=1"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(Teacher[].class));
    }
    @Test
    public void testCallCanvasApiForScoreByID() {
        Score score1 = new Score(1L, 90.0, null, 1L, Timestamp.valueOf("2023-09-01 10:10:10"), false, false, false, 90.0, 10.0, 1L, 90.0);
        ResponseEntity<Score[]> responseEntity = new ResponseEntity<>(new Score[]{score1}, HttpStatus.OK);

        when(restTemplate.exchange(
                eq("null/courses/1/assignments/2/submissions?per_page=50&page=1"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(Score[].class)
        ))
                .thenReturn(responseEntity);
        List<Score> result = canvasService.callCanvasApiForScoreByID(2L, 1L);
        verify(restTemplate, times(1)).exchange(
                eq("null/courses/1/assignments/2/submissions?per_page=50&page=1"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(Score[].class)
        );
        assertNotNull(result);
        assertEquals(1, result.size());
        Score returnedScore = result.get(0);
        assertEquals(1L, returnedScore.getId().longValue());
        assertEquals(90.0, returnedScore.getScore(), 0.01);
    }
    @Test
    void testCallCanvasApiForStudentByCourseID() {
        Student student1 = new Student(1L, 1L, "Alice", "sisUserId", "loginId", "Student", 1L, "alice@example.com");
        ResponseEntity<Student[]> responseEntity = new ResponseEntity<>(new Student[]{student1}, HttpStatus.OK);

        when(restTemplate.exchange(
                eq("null/courses/1/users?enrollment_type[]=student&enrollment_role_id=3&per_page=50&page=1"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(Student[].class)
        ))
                .thenReturn(responseEntity);

        List<Student> result = canvasService.callCanvasApiForStudentByCourseID(1L);

        verify(restTemplate, times(1)).exchange(
                eq("null/courses/1/users?enrollment_type[]=student&enrollment_role_id=3&per_page=50&page=1"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(Student[].class)
        );
        assertNotNull(result);
        assertEquals(1, result.size());
        Student returnedStudent = result.get(0);
        assertEquals(1L, returnedStudent.getId().longValue());
        assertEquals("Alice", returnedStudent.getName());
    }
    @Test
    void testCallCanvasApiForAssignmentByCourseID() {
        Assignment assignment1 = new Assignment(1L, "Assignment1_Pol_T123", Timestamp.valueOf("2023-09-01 10:10:10"), Timestamp.valueOf("2023-09-01 10:10:10"), Timestamp.valueOf("2023-09-01 10:10:10"), new BigDecimal(100), Timestamp.valueOf("2023-09-01 10:10:10"), Timestamp.valueOf("2023-09-01 10:10:10"), true, 1L, "Assignment1_Pol_T123", 10, "tutorial", 10, "weight", "complete");
        ResponseEntity<Assignment[]> responseEntity = new ResponseEntity<>(new Assignment[]{assignment1}, HttpStatus.OK);

        when(restTemplate.exchange(
                eq("null/courses/1/assignments?per_page=50&page=1"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(Assignment[].class)
        ))
                .thenReturn(responseEntity);

        List<Assignment> result = canvasService.callCanvasApiForAssignmentByCourseID(1L);
        System.out.println(result);
        verify(restTemplate, times(1)).exchange(
                eq("null/courses/1/assignments?per_page=50&page=1"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(Assignment[].class)
        );

        assertNotNull(result);
        assertEquals(1, result.size());
        Assignment returnedAssignment = result.get(0);
        assertEquals(1L, returnedAssignment.getId().longValue());
        assertEquals("Assignment1_Pol_T123", returnedAssignment.getName());
        assertEquals("tutorial", returnedAssignment.getType());
        assertEquals(DEFAULT_TUTORIAL_WEIGHT, returnedAssignment.getDefaultParticipationWeighting());
    }
    @Test
    void testCallCanvasApiForCurrentUser() {
        User testUser = new User(1L, "John Doe", "avatarUrl", "Doe", "John", "token");
        ResponseEntity<User> responseEntity = new ResponseEntity<>(testUser, HttpStatus.OK);
        canvasService.setAccessToken("token");
        when(restTemplate.exchange(
                eq("null/users/self"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(User.class)
        ))
                .thenReturn(responseEntity);

        User result = canvasService.callCanvasApiForCurrentUser();

        verify(restTemplate, times(1)).exchange(
                eq("null/users/self"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(User.class)
        );

        assertNotNull(result);
        assertEquals(1L, result.getId().longValue());
        assertEquals("John Doe", result.getName());
        assertEquals("avatarUrl", result.getAvatarUrl());
        assertEquals("token", result.getToken());
    }

}

