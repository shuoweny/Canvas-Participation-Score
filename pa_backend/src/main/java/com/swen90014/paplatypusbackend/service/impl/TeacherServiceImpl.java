package com.swen90014.paplatypusbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swen90014.paplatypusbackend.dao.TeacherDao;
import com.swen90014.paplatypusbackend.domain.Teacher;
import com.swen90014.paplatypusbackend.dto.AccessDTO;
import com.swen90014.paplatypusbackend.service.ITeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherDao, Teacher> implements ITeacherService {
    private final TeacherDao teacherDao;
    @Autowired
    public TeacherServiceImpl(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }

    @Override
    public void saveAllTeacher(List<Teacher> teachers) {
        teacherDao.insertOrUpdateBatch(teachers);
    }

    @Override
    public List<Teacher> getTeacherByName(String name) {
        return teacherDao.getTeacherByName(name);
    }

    @Override
    public List<Teacher> getTeacherByUserLogin(String login_id) {
        return teacherDao.getTeacherByUserLogin(login_id);
    }

    @Override
    public List<Teacher> getTeacherByUserNumber(String sis_user_id) {
        return teacherDao.getTeacherByUserNumber(sis_user_id);
    }

    @Override
    public List<AccessDTO> getTeacherAccessById(Long user_id) {
        return teacherDao.getTeacherAccessById(user_id);
    }

    @Override
    public List<Teacher> getTeacherByCourseId(Long course_id) {
        return teacherDao.getTeacherByCourseId(course_id);
    }

    @Override
    public void updateAccess(Long courseId, Long userId, boolean access) {
        teacherDao.updateAccess(courseId, userId, access);
    }


}
