package com.swen90014.paplatypusbackend.controller;

import com.swen90014.paplatypusbackend.controller.utils.ResultUtil;
import com.swen90014.paplatypusbackend.domain.Assignment;
import com.swen90014.paplatypusbackend.domain.Enrollment;
import com.swen90014.paplatypusbackend.service.impl.AssignmentServiceImpl;
import com.swen90014.paplatypusbackend.service.impl.EnrollmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/enrollment")
public class EnrollmentController {
    @Autowired
    private EnrollmentServiceImpl enrollmentService;
    @GetMapping("/getAll")
    public ResultUtil getAll(){
        List<Enrollment> enrollmentList = enrollmentService.list();
        int state = enrollmentList.isEmpty()?500:200;
        String message = enrollmentList.isEmpty()?"FAIL":"SUCCESS";
        return new ResultUtil(state, enrollmentList, message);
    }
    @GetMapping("/getAssignmentById/{id}")
    public ResultUtil getById(@PathVariable Long id){
        Enrollment enrollment = enrollmentService.getById(id);
        int state = enrollment==null?500:200;
        String message = enrollment==null?"FAIL":"SUCCESS";
        return new ResultUtil(state, enrollment, message);
    }
    @GetMapping("/getEnrollmentByCourseId/{course_id}")
    public ResultUtil getEnrollmentByCourseId(@PathVariable Long course_id){
        List<Enrollment> enrollmentList = enrollmentService.getEnrollmentByCourseId(course_id);
        int state = enrollmentList.isEmpty()?500:200;
        String message = enrollmentList.isEmpty()?"FAIL":"SUCCESS";
        return new ResultUtil(state, enrollmentList, message);
    }
    @GetMapping("/getEnrollmentByUserId/{user_id}")
    public ResultUtil getEnrollmentByUserId(@PathVariable Long user_id){
        List<Enrollment> enrollmentList = enrollmentService.getEnrollmentByUserId(user_id);
        int state = enrollmentList.isEmpty()?500:200;
        String message = enrollmentList.isEmpty()?"FAIL":"SUCCESS";
        return new ResultUtil(state, enrollmentList, message);
    }

}
