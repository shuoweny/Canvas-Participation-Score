package com.swen90014.paplatypusbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.swen90014.paplatypusbackend.domain.Course;
import com.swen90014.paplatypusbackend.domain.Score;

import java.util.List;

public interface ICourseService extends IService<Course> {
    void saveAllCourses(List<Course> courses);
    List<Course> getCourseByCourseName(String name);
    List<Course> getCourseByCourseCode(String course_code);
    List<Long> selectAllIds();
    List<Course> selectCourseByUserId(Long userId);
}
