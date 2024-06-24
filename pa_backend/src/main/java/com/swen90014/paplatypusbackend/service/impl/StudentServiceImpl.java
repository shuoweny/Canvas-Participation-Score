
package com.swen90014.paplatypusbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swen90014.paplatypusbackend.dao.StudentDao;
import com.swen90014.paplatypusbackend.domain.Student;
import com.swen90014.paplatypusbackend.dto.StudentDTO;
import com.swen90014.paplatypusbackend.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentDao, Student> implements IStudentService {
    private final StudentDao studentDao;
    @Autowired
    public StudentServiceImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public void saveAllStudent(List<Student> students) {
        studentDao.insertOrUpdateBatch(students);
    }

    @Override
    public List<Student> getStudentByStudentName(String name) {
        return studentDao.getStudentByStudentName(name);
    }

    @Override
    public List<Student> getStudentByStudentNumber(String sis_user_id) {
        return studentDao.getStudentByStudentNumber(sis_user_id);
    }

    @Override
    public List<Student> getStudentByUserLogin(String login_id) {
        return studentDao.getStudentByUserLogin(login_id);
    }

    @Override
    public List<Long> getStudentIdByCourseId(Long course_id) {
        return studentDao.getStudentIdByCourseId(course_id);
    }

    @Override
    public List<Student> getStudentByCourseId(Long course_id) {
        return studentDao.getStudentByCourseId(course_id);
    }

    @Override
    public String getStudentNameById(Long sid) {
        return studentDao.getStudentNameById(sid);
    }

    @Override
    public List<StudentDTO> findStudentScoresByCourseId(Long courseId) {
        return studentDao.findStudentScoresByCourseId(courseId);
    }
}
