package com.swen90014.paplatypusbackend;

import com.swen90014.paplatypusbackend.dao.StudentDao;
import com.swen90014.paplatypusbackend.domain.Student;
import com.swen90014.paplatypusbackend.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class StudentServiceTest {

    @InjectMocks
    private StudentServiceImpl studentService;

    @Mock
    private StudentDao studentDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveAllStudent() {
        List<Student> students = new ArrayList<>();

        Student student1 = new Student();
        student1.setId(1L);
        student1.setName("John Doe");
        student1.setSisUserId("101");

        Student student2 = new Student();
        student2.setId(2L);
        student2.setName("Jane Smith");
        student2.setSisUserId("102");

        students.add(student1);
        students.add(student2);

        doNothing().when(studentDao).insertOrUpdateBatch(students);

        studentService.saveAllStudent(students);

        verify(studentDao, times(1)).insertOrUpdateBatch(students);
    }

    @Test
    void testGetStudentByStudentName() {
        String studentName = "John Doe";

        List<Student> mockStudents = new ArrayList<>();
        Student student = new Student();
        student.setId(1L);
        student.setName(studentName);
        student.setSisUserId("101");
        mockStudents.add(student);

        when(studentDao.getStudentByStudentName(studentName)).thenReturn(mockStudents);

        List<Student> result = studentService.getStudentByStudentName(studentName);

        assertEquals(mockStudents, result);
    }

    @Test
    void testGetStudentByStudentNumber() {
        String sisUserId = "101";

        List<Student> mockStudents = new ArrayList<>();
        Student student = new Student();
        student.setId(1L);
        student.setName("John Doe");
        student.setSisUserId(sisUserId);
        mockStudents.add(student);

        when(studentDao.getStudentByStudentNumber(sisUserId)).thenReturn(mockStudents);

        List<Student> result = studentService.getStudentByStudentNumber(sisUserId);

        assertEquals(mockStudents, result);
    }

    @Test
    void testGetStudentByUserLogin() {
        String loginId = "johndoe";

        List<Student> mockStudents = new ArrayList<>();
        Student student = new Student();
        student.setId(1L);
        student.setName("John Doe");
        student.setSisUserId("101");
        mockStudents.add(student);

        when(studentDao.getStudentByUserLogin(loginId)).thenReturn(mockStudents);

        List<Student> result = studentService.getStudentByUserLogin(loginId);

        assertEquals(mockStudents, result);
    }

    @Test
    void testGetStudentIdByCourseId() {
        Long courseId = 1L;

        List<Long> mockStudentIds = new ArrayList<>();
        mockStudentIds.add(101L);
        mockStudentIds.add(102L);

        when(studentDao.getStudentIdByCourseId(courseId)).thenReturn(mockStudentIds);

        List<Long> result = studentService.getStudentIdByCourseId(courseId);

        assertEquals(mockStudentIds, result);
    }
}
