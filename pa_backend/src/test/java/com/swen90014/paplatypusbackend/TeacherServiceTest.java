package com.swen90014.paplatypusbackend;

import com.swen90014.paplatypusbackend.dao.TeacherDao;
import com.swen90014.paplatypusbackend.domain.Teacher;
import com.swen90014.paplatypusbackend.dto.AccessDTO;
import com.swen90014.paplatypusbackend.service.impl.TeacherServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TeacherServiceTest {

    @InjectMocks
    private TeacherServiceImpl teacherService;

    @Mock
    private TeacherDao teacherDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveAllTeacher() {
        List<Teacher> teachers = new ArrayList<>();

        Teacher teacher1 = new Teacher();
        teacher1.setId(1L);
        teacher1.setName("John Teacher");
        teacher1.setSisUserId("201");

        Teacher teacher2 = new Teacher();
        teacher2.setId(2L);
        teacher2.setName("Jane Teacher");
        teacher2.setSisUserId("202");

        teachers.add(teacher1);
        teachers.add(teacher2);

        doNothing().when(teacherDao).insertOrUpdateBatch(teachers);

        teacherService.saveAllTeacher(teachers);

        verify(teacherDao, times(1)).insertOrUpdateBatch(teachers);
    }

    @Test
    void testGetTeacherByName() {
        String teacherName = "John Teacher";

        List<Teacher> mockTeachers = new ArrayList<>();
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName(teacherName);
        teacher.setSisUserId("201");
        mockTeachers.add(teacher);

        when(teacherDao.getTeacherByName(teacherName)).thenReturn(mockTeachers);

        List<Teacher> result = teacherService.getTeacherByName(teacherName);

        assertEquals(mockTeachers, result);
    }

    @Test
    void testGetTeacherByUserLogin() {
        String loginId = "johnteacher";

        List<Teacher> mockTeachers = new ArrayList<>();
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("John Teacher");
        teacher.setSisUserId("201");
        mockTeachers.add(teacher);

        when(teacherDao.getTeacherByUserLogin(loginId)).thenReturn(mockTeachers);

        List<Teacher> result = teacherService.getTeacherByUserLogin(loginId);

        assertEquals(mockTeachers, result);
    }

    @Test
    void testGetTeacherByUserNumber() {
        String sisUserId = "201";

        List<Teacher> mockTeachers = new ArrayList<>();
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("John Teacher");
        teacher.setSisUserId(sisUserId);
        mockTeachers.add(teacher);

        when(teacherDao.getTeacherByUserNumber(sisUserId)).thenReturn(mockTeachers);

        List<Teacher> result = teacherService.getTeacherByUserNumber(sisUserId);

        assertEquals(mockTeachers, result);
    }

    @Test
    void testGetTeacherAccessById() {
        Long userId = 1L;

        List<AccessDTO> mockAccessList = new ArrayList<>();
        AccessDTO access = new AccessDTO();
        access.setAccess(true);
        mockAccessList.add(access);

        when(teacherDao.getTeacherAccessById(userId)).thenReturn(mockAccessList);

        List<AccessDTO> result = teacherService.getTeacherAccessById(userId);

        assertEquals(mockAccessList, result);
    }

    @Test
    void testUpdateAccess() {
        Long courseId = 101L;
        Long userId = 1L;
        boolean access = true;

        teacherService.updateAccess(courseId, userId, access);

        verify(teacherDao, times(1)).updateAccess(courseId, userId, access);
    }
}

