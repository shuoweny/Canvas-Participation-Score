package com.swen90014.paplatypusbackend.controller;

import com.swen90014.paplatypusbackend.controller.utils.ResultUtil;
import com.swen90014.paplatypusbackend.domain.Course;
import com.swen90014.paplatypusbackend.domain.User;
import com.swen90014.paplatypusbackend.service.impl.CourseServiceImpl;
import com.swen90014.paplatypusbackend.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseServiceImpl courseService;
    @GetMapping("/getAll")
    public ResultUtil getAll(){
        List<Course> courseList = courseService.list();
        int state = courseList.isEmpty()?500:200;
        String message = courseList.isEmpty()?"FAIL":"SUCCESS";
        return new ResultUtil(state, courseList, message);
    }
    @GetMapping("/getCourseById/{id}")
    public ResultUtil getById(@PathVariable Long id){
        Course course = courseService.getById(id);
        int state = course==null?500:200;
        String message = course==null?"FAIL":"SUCCESS";
        return new ResultUtil(state, course, message);
    }
    @GetMapping("/getCourseByCourseName/{name}")
    public ResultUtil getCourseByCourseName(@PathVariable String name){
        List<Course> courseList = courseService.getCourseByCourseName(name);
        int state = courseList.isEmpty()?500:200;
        String message = courseList.isEmpty()?"FAIL":"SUCCESS";
        return new ResultUtil(state, courseList, message);
    }
    @GetMapping("/getCourseByCourseCode/{course_code}")
    public ResultUtil getCourseByCourseCode(@PathVariable String course_code){
        List<Course> courseList = courseService.getCourseByCourseCode(course_code);
        int state = courseList.isEmpty()?500:200;
        String message = courseList.isEmpty()?"FAIL":"SUCCESS";
        return new ResultUtil(state, courseList, message);
    }
}
