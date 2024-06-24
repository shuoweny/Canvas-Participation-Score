package com.swen90014.paplatypusbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swen90014.paplatypusbackend.dao.EnrollmentDao;
import com.swen90014.paplatypusbackend.domain.Enrollment;
import com.swen90014.paplatypusbackend.service.IEnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentServiceImpl extends ServiceImpl<EnrollmentDao, Enrollment> implements IEnrollmentService {
    private EnrollmentDao enrollmentDao;
    @Autowired
    public EnrollmentServiceImpl(EnrollmentDao enrollmentDao) {
        this.enrollmentDao = enrollmentDao;
    }

    @Override
    public void saveAllEnrollment(List<Enrollment> enrollments) {
        enrollmentDao.insertOrUpdateBatch(enrollments);
    }

    @Override
    public List<Enrollment> getEnrollmentByCourseId(Long course_id) {
        return enrollmentDao.getEnrollmentByCourseId(course_id);
    }

    @Override
    public List<Enrollment> getEnrollmentByUserId(Long user_id) {
        return enrollmentDao.getEnrollmentByUserId(user_id);
    }
}
