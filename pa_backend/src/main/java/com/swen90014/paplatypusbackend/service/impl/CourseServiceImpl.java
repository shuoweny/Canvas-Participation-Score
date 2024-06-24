package com.swen90014.paplatypusbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swen90014.paplatypusbackend.dao.CourseDao;
import com.swen90014.paplatypusbackend.domain.Course;
import com.swen90014.paplatypusbackend.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseDao, Course> implements ICourseService {
    private final CourseDao courseDao;
    @Autowired
    public CourseServiceImpl(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    @Override
    public void saveAllCourses(List<Course> courses) {
        courseDao.insertOrUpdateBatch(courses);
    }

    @Override
    public List<Course> getCourseByCourseName(String name) {
        return courseDao.getCourseByCourseName(name);
    }

    @Override
    public List<Course> getCourseByCourseCode(String course_code) {
        return courseDao.getCourseByCourseCode(course_code);
    }

    @Override
    public List<Long> selectAllIds() {
        return courseDao.selectAllIds();
    }

    @Override
    public List<Course> selectCourseByUserId(Long userId) {
        return courseDao.selectCourseByUserId(userId);
    }

}
