package com.swen90014.paplatypusbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.swen90014.paplatypusbackend.domain.Enrollment;

import java.util.List;

public interface IEnrollmentService extends IService<Enrollment> {
    void saveAllEnrollment(List<Enrollment> enrollments);
    List<Enrollment> getEnrollmentByCourseId(Long course_id);
    List<Enrollment> getEnrollmentByUserId(Long user_id);
}
