package com.swen90014.paplatypusbackend.controller;

import com.swen90014.paplatypusbackend.controller.utils.ResultUtil;
import com.swen90014.paplatypusbackend.domain.Student;
import com.swen90014.paplatypusbackend.dto.StudentDTO;
import com.swen90014.paplatypusbackend.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private IStudentService iStudentService;

    @GetMapping("/getAll")
    public ResultUtil getAll(){
        List<Student> studentList = iStudentService.list();
        int state = studentList.isEmpty()?500:200;
        String message = studentList.isEmpty()?"FAIL":"SUCCESS";
        return new ResultUtil(state, studentList, message);
    }

    @GetMapping("/getStudentById/{id}")
    public ResultUtil getById(@PathVariable Long id){
        Student subject = iStudentService.getById(id);
        int state = subject != null?200:500;
        String message = subject != null?"SUCCESS":"FAIL";
        return new ResultUtil(state, subject,message);
    }
    @GetMapping("/getStudentByStudentName/{name}")
    public ResultUtil getStudentByStudentName(@PathVariable String name){
        List<Student> studentList = iStudentService.getStudentByStudentName(name);
        int state = studentList.isEmpty()?500:200;
        String message = studentList.isEmpty()?"FAIL":"SUCCESS";
        return new ResultUtil(state, studentList, message);
    }
    @GetMapping("/getStudentByStudentNumber/{sis_user_id}")
    public ResultUtil getStudentByStudentNumber(@PathVariable String sis_user_id){
        List<Student> studentList = iStudentService.getStudentByStudentNumber(sis_user_id);
        int state = studentList.isEmpty()?500:200;
        String message = studentList.isEmpty()?"FAIL":"SUCCESS";
        return new ResultUtil(state, studentList, message);
    }
    @GetMapping("/getStudentByUserLogin/{login_id}")
    public ResultUtil getStudentByUserLogin(@PathVariable String login_id){
        List<Student> studentList = iStudentService.getStudentByUserLogin(login_id);
        int state = studentList.isEmpty()?500:200;
        String message = studentList.isEmpty()?"FAIL":"SUCCESS";
        return new ResultUtil(state, studentList, message);
    }

    @GetMapping("/getStudentByCourseId/{course_id}")
    public ResultUtil getStudentByUserLogin(@PathVariable Long course_id){
        List<Student> studentList = iStudentService.getStudentByCourseId(course_id);
        int state = studentList.isEmpty()?500:200;
        String message = studentList.isEmpty()?"FAIL":"SUCCESS";
        return new ResultUtil(state, studentList, message);
    }

    @GetMapping("/getStudentSummaryByCourseId/{course_id}")
    public ResultUtil getStudentSummaryByCourseId(@PathVariable Long course_id){
        List<StudentDTO> studentList = iStudentService.findStudentScoresByCourseId(course_id);
        int state = studentList.isEmpty()?500:200;
        String message = studentList.isEmpty()?"FAIL":"SUCCESS";
        return new ResultUtil(state, studentList, message);
    }

}
