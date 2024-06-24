package com.swen90014.paplatypusbackend.service.impl;
import com.fasterxml.jackson.databind.JsonNode;
import com.swen90014.paplatypusbackend.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
public class CanvasService {
    @Value("${canvas.domain}")
    private String canvasDomain;

    @Value("${canvas.access.token}")
    private String accessToken;
    private final RestTemplate restTemplate;
    private final UserServiceImpl userService;
    private final TeacherServiceImpl teacherService;
    private final StudentServiceImpl studentService;
    private final ScoreServiceImpl scoreService;
    private final EnrollmentServiceImpl enrollmentService;
    private final CourseServiceImpl courseService;
    private final AssignmentServiceImpl assignmentService;
    private static Map<Integer, String> STUDENT_ROLE_MAP;
    private static Map<Integer, String> TEACHER_ROLE_MAP;
    private Integer currentTutorialWeight = 15;
    private Integer currentLectureWeight = 60;
    private Integer currentH5PWeight = 30;
    private Integer currentCanvasWeight = 30;
    public static final Integer DEFAULT_TUTORIAL_WEIGHT = 15;
    private static final Integer DEFAULT_LECTURE_WEIGHT = 60;
    private static final Integer DEFAULT_H5P_WEIGHT = 30;
    private static final Integer DEFAULT_CANVAS_WEIGHT = 30;
    private static final String DEFAULT_METHOD = "complete";


    public static Map<Integer, String> getStudentRoleMap() {
        return STUDENT_ROLE_MAP;
    }

    public static void setStudentRoleMap(Map<Integer, String> studentRoleMap) {
        STUDENT_ROLE_MAP = studentRoleMap;
    }

    public static Map<Integer, String> getTeacherRoleMap() {
        return TEACHER_ROLE_MAP;
    }

    public static void setTeacherRoleMap(Map<Integer, String> teacherRoleMap) {
        TEACHER_ROLE_MAP = teacherRoleMap;
    }

    static {
        STUDENT_ROLE_MAP = new HashMap<>();
        STUDENT_ROLE_MAP.put(3, "Student");
        STUDENT_ROLE_MAP.put(47, "None-Accredited Learner");
        STUDENT_ROLE_MAP.put(33, "External User");
        TEACHER_ROLE_MAP = new HashMap<>();
        TEACHER_ROLE_MAP.put(4, "Instructor");
        TEACHER_ROLE_MAP.put(39, "Enroller");
        TEACHER_ROLE_MAP.put(21, "Subject Coordinator");
        TEACHER_ROLE_MAP.put(5, "Tutor");
        TEACHER_ROLE_MAP.put(59, "Marker");
        TEACHER_ROLE_MAP.put(6, "Subject Designer");
        TEACHER_ROLE_MAP.put(7, "Auditor");
        TEACHER_ROLE_MAP.put(48, "Auditor");
        TEACHER_ROLE_MAP.put(45, "ITAS Tutor");
        TEACHER_ROLE_MAP.put(46, "College Tutor");
    }
    @Autowired
    public CanvasService(RestTemplate restTemplate, UserServiceImpl userService, TeacherServiceImpl teacherService,
                         StudentServiceImpl studentService, ScoreServiceImpl scoreService,
                         EnrollmentServiceImpl enrollmentService, CourseServiceImpl courseService,
                         AssignmentServiceImpl assignmentService) {
        this.restTemplate = restTemplate;
        this.userService = userService;
        this.teacherService = teacherService;
        this.studentService = studentService;
        this.scoreService = scoreService;
        this.enrollmentService = enrollmentService;
        this.courseService = courseService;
        this.assignmentService = assignmentService;
    }
    private void saveDataFromCanvas(){

        User currentUser = callCanvasApiForCurrentUser();
        userService.insertOrUpdate(currentUser);
        List<Course> courses = callCanvasApiForCourses(currentUser);
        courseService.saveAllCourses(courses);
        List<Enrollment> enrollments = callCanvasApiForEnrollment();
        enrollmentService.saveAllEnrollment(enrollments);
        for (Course course : courses) {
            List<Teacher> teachers = callCanvasApiForTeacherByCourseID(course.getId());
            teacherService.saveAllTeacher(teachers);
            List<Student> students = callCanvasApiForStudentByCourseID(course.getId());
            studentService.saveAllStudent(students);
            List<Assignment> assignments = callCanvasApiForAssignmentByCourseID(course.getId());
            assignmentService.saveAllAssignment(assignments);
            for (Assignment assignment : assignments) {
                List<Score> scores = callCanvasApiForScoreByID(assignment.getId(), course.getId());
                scoreService.saveAllScore(scores);
            }

        }
    }
    @Async
    @Transactional(rollbackFor = Exception.class)
    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 5000))
    public CompletableFuture<Boolean> saveData() {
        System.out.println("save start");

            saveDataFromCanvas();
            List<Long> idList = courseService.selectAllIds();
            for (Long id: idList){
                scoreService.setParticipationScoreByScore(id);
                scoreService.setParticipationScoreByComplete(id);
            }
            System.out.println("save end");
            return CompletableFuture.completedFuture(true);
    }

    public List<Course> callCanvasApiForCourses(User currentUser) {
        List<Course> courseList = new ArrayList<>();
        if (currentUser == null) {
            System.err.println("currentUser is null");
            return courseList;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        int page = 1;
        int perPage = 50;

        try {
            while (true) {
                String canvasApiUrl = canvasDomain + "/courses?per_page=" + perPage + "&page=" + page;
                ResponseEntity<Course[]> response = restTemplate.exchange(
                        canvasApiUrl,
                        HttpMethod.GET,
                        entity,
                        Course[].class
                );
                Course[] courses = response != null ? response.getBody() : null;
                if (courses != null && courses.length > 0) {
                    System.out.println(Arrays.toString(courses));
                    for (Course course : courses) {
                        if (course != null) {
                            course.setUserId(currentUser.getId());
                            courseList.add(course);
                        } else {
                            System.err.println("One of the courses in the array is null");
                        }
                    }
                    page++;
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return courseList;
    }


    public List<Enrollment> callCanvasApiForEnrollment() {
        List<Enrollment> enrollmentList = new ArrayList<>();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        int page = 1;
        int perPage = 50;

        try {
            while (true) {
                String canvasApiUrl = canvasDomain + "/courses?per_page=" + perPage + "&page=" + page;
                ResponseEntity<JsonNode> response = restTemplate.exchange(
                        canvasApiUrl,
                        HttpMethod.GET,
                        entity,
                        JsonNode.class
                );
                if (response != null && response.getBody() != null) {
                    JsonNode responseBody = response.getBody();
                    if (responseBody.isArray() && responseBody.size() > 0) {
                        for (JsonNode courseNode : responseBody) {
                            Integer courseId = courseNode.get("id") != null ? courseNode.get("id").asInt() : null;
                            JsonNode enrollmentsNode = courseNode.get("enrollments");
                            if (enrollmentsNode != null && enrollmentsNode.isArray()) {
                                for (JsonNode enrollmentNode : enrollmentsNode) {
                                    Enrollment enrollment = new Enrollment();
                                    if (courseId != null) {
                                        enrollment.setCourseId((long) courseId);
                                        enrollment.setUserId(enrollmentNode.get("user_id") != null ? enrollmentNode.get("user_id").asLong() : null);
                                        enrollment.setType(enrollmentNode.get("type") != null ? enrollmentNode.get("type").asText() : null);
                                        enrollment.setEnrollmentState(enrollmentNode.get("enrollment_state") != null ? enrollmentNode.get("enrollment_state").asText() : null);
                                        enrollmentList.add(enrollment);
                                    }
                                }
                            }
                        }
                        page++;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return enrollmentList;
    }



    public List<Teacher> callCanvasApiForTeacherByCourseID(Long courseId) {
        List<Teacher> teacherList = new ArrayList<>();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        if (courseId == null || TEACHER_ROLE_MAP == null) {
            return teacherList; // Return empty list or consider throwing an exception
        }

        for (Integer roleId : TEACHER_ROLE_MAP.keySet()) {
            if (roleId != null) {
                int page = 1;
                int perPage = 50;
                while (true) {
                    String canvasApiUrl = canvasDomain + "/courses/" + courseId + "/users?enrollment_type[]=teacher&enrollment_role_id=" + roleId + "&per_page=" + perPage + "&page=" + page;
                    ResponseEntity<Teacher[]> response = restTemplate.exchange(canvasApiUrl, HttpMethod.GET, entity, Teacher[].class);

                    if (response != null && response.getBody() != null && response.getBody().length > 0) {
                        for (Teacher teacher : response.getBody()) {
                            if (teacher != null) {
                                String type = TEACHER_ROLE_MAP.get(roleId);
                                teacher.setType(type);
                                teacher.setCourseId(courseId);
                                teacherList.add(teacher);
                            }
                        }
                        page++;
                    } else {
                        break;
                    }
                }
            }
        }
        return teacherList;
    }


    public List<Score> callCanvasApiForScoreByID(Long assignmentId, Long courseId){
        if (assignmentId == null || courseId == null) {
            return new ArrayList<>(); // Return empty list or consider throwing an exception
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        int page = 1;
        int perPage = 50;
        List<Score> scoreList = new ArrayList<>();
        try {
            while (true) {
                String canvasApiUrl = canvasDomain + "/courses/" + courseId + "/assignments/"+assignmentId+"/submissions?per_page=" + perPage + "&page=" + page;
                ResponseEntity<Score[]> response = restTemplate.exchange(
                        canvasApiUrl,
                        HttpMethod.GET,
                        entity,
                        Score[].class
                );

                if (response != null && response.getBody() != null) {
                    Score[] scores = response.getBody();
                    if (scores.length > 0) {
                        for(Score score : scores) {
                            if (score != null) {
                                score.setCourseId(courseId);
                                scoreList.add(score);
                            }
                        }
                        page++;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scoreList;
    }

    public List<Student> callCanvasApiForStudentByCourseID(Long courseId) {
        if (courseId == null) {
            return new ArrayList<>();
        }
        List<Student> studentList = new ArrayList<>();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        if (STUDENT_ROLE_MAP == null) {
            return new ArrayList<>();
        }

        for (Integer roleId : STUDENT_ROLE_MAP.keySet()) {
            if (roleId == null) {
                continue;
            }

            int page = 1;
            int perPage = 50;
            while (true) {
                String canvasApiUrl = canvasDomain + "/courses/" + courseId + "/users?enrollment_type[]=student&enrollment_role_id=" + roleId + "&per_page=" + perPage + "&page=" + page;
                ResponseEntity<Student[]> response = restTemplate.exchange(canvasApiUrl, HttpMethod.GET, entity, Student[].class);

                if (response != null && response.getBody() != null) {
                    Student[] students = response.getBody();

                    if (students.length > 0) {
                        for (Student student : students) {
                            if (student != null) {
                                String type = STUDENT_ROLE_MAP.get(roleId);
                                if (type != null) {
                                    student.setType(type);
                                    student.setCourseId(courseId);
                                    studentList.add(student);
                                }
                            }
                        }
                        page++;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        return studentList;
    }

    public List<Assignment> callCanvasApiForAssignmentByCourseID(Long courseId) {
        if (courseId == null) {
            // Handle the case where courseId is null, log, or throw an exception as appropriate
            return Collections.emptyList();
        }

        List<Assignment> assignments = new ArrayList<>();
        int page = 1;
        int perPage = 50;
        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        while (true) {
            String canvasApiUrl = canvasDomain + "/courses/" + courseId + "/assignments?per_page=" + perPage + "&page=" + page;
            ResponseEntity<Assignment[]> response = restTemplate.exchange(canvasApiUrl, HttpMethod.GET, entity, Assignment[].class);

            if (response != null && response.getBody() != null) {
                Assignment[] responseAssignments = response.getBody();

                if (responseAssignments.length > 0) {
                    List<Assignment> pageAssignments = Arrays.asList(responseAssignments);

                    for (Assignment assignment : pageAssignments) {

                        if (assignment != null && assignment.getName() != null) {
                            String name = assignment.getName();
                            assignment.setDefaultMethod(DEFAULT_METHOD);
                            if (name.contains("_Pol_")) {
                                String classCode = name.split("_")[2];
                                assignment.setType(classCode.startsWith("T") ? "tutorial" : "lecture");
                                assignment.setDefaultParticipationWeighting(classCode.startsWith("T") ? DEFAULT_TUTORIAL_WEIGHT : DEFAULT_LECTURE_WEIGHT);
                            } else if (name.contains("_H5P_")) {
                                assignment.setType("h5p");
                                assignment.setDefaultParticipationWeighting(DEFAULT_H5P_WEIGHT);
                            } else if (name.contains("_Can_")) {
                                assignment.setType("canvas");
                                assignment.setDefaultParticipationWeighting(DEFAULT_CANVAS_WEIGHT);
                            }
                            else{
                                assignment.setType("other");
                                assignment.setDefaultParticipationWeighting(DEFAULT_CANVAS_WEIGHT);
                            }
                        }
                    }
                    assignments.addAll(pageAssignments);
                    page++;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        return assignments;
    }



    public User callCanvasApiForCurrentUser() {
        try {
            String canvasApiUrl = canvasDomain + "/users/self";
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);
            HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
            System.out.println(canvasApiUrl);
            ResponseEntity<User> response = restTemplate.exchange(
                    canvasApiUrl,
                    HttpMethod.GET,
                    entity,
                    User.class
            );
            User user = response.getBody();
            System.out.println(user.toString());

            if (user != null && response.getStatusCode() == HttpStatus.OK) {
                user.setToken(accessToken);
                return user;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
