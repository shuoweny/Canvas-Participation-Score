package com.swen90014.paplatypusbackend.controller;

import com.swen90014.paplatypusbackend.controller.utils.ResultUtil;
import com.swen90014.paplatypusbackend.domain.Teacher;
import com.swen90014.paplatypusbackend.dto.AccessDTO;
import com.swen90014.paplatypusbackend.service.impl.TeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    private TeacherServiceImpl teacherService;
    @GetMapping("/getAll")
    public ResultUtil getAll(){
        List<Teacher> teacherList = teacherService.list();
        int state = teacherList.isEmpty()?500:200;
        String message = teacherList.isEmpty()?"FAIL":"SUCCESS";
        return new ResultUtil(state, teacherList, message);
    }

    @GetMapping("/getTeacherById/{id}")
    public ResultUtil getById(@PathVariable Long id){
        Teacher teacher = teacherService.getById(id);
        int state = teacher != null?200:500;
        String message = teacher != null?"SUCCESS":"FAIL";
        return new ResultUtil(state, teacher, message);
    }
    @GetMapping("/getTeacherByName/{name}")
    public ResultUtil getTeacherByName(@PathVariable String name){
        List<Teacher> teacherList = teacherService.getTeacherByName(name);
        int state = teacherList.isEmpty()?500:200;
        String message = teacherList.isEmpty()?"FAIL":"SUCCESS";
        return new ResultUtil(state, teacherList, message);
    }
    @GetMapping("/getTeacherByUserNumber/{sis_user_id}")
    public ResultUtil getTeacherByUserNumber(@PathVariable String sis_user_id){
        List<Teacher> teacherList = teacherService.getTeacherByUserNumber(sis_user_id);
        int state = teacherList.isEmpty()?500:200;
        String message = teacherList.isEmpty()?"FAIL":"SUCCESS";
        return new ResultUtil(state, teacherList, message);
    }
    @GetMapping("/getTeacherByUserLogin/{login_id}")
    public ResultUtil getTeacherByUserLogin(@PathVariable String login_id){
        List<Teacher> teacherList = teacherService.getTeacherByUserLogin(login_id);
        int state = teacherList.isEmpty()?500:200;
        String message = teacherList.isEmpty()?"FAIL":"SUCCESS";
        return new ResultUtil(state, teacherList, message);
    }

    @GetMapping("/getTeacherAccess/{user_id}")
    public ResultUtil getTeacherAccess(@PathVariable Long user_id){
        List<AccessDTO> teacherList = teacherService.getTeacherAccessById(user_id);
        int state = teacherList.isEmpty()?500:200;
        String message = teacherList.isEmpty()?"FAIL":"SUCCESS";
        return new ResultUtil(state, teacherList, message);
    }

    @GetMapping("/getTeacherByCourseId/{course_id}")
    public ResultUtil getTeacherByCourseId(@PathVariable Long course_id){
        List<Teacher> teacherList = teacherService.getTeacherByCourseId(course_id);
        int state = teacherList.isEmpty()?500:200;
        String message = teacherList.isEmpty()?"FAIL":"SUCCESS";
        return new ResultUtil(state, teacherList, message);
    }

    @PostMapping("/setTeacherAccess/{course_id}/{user_id}")
    public ResultUtil setTeacherAccess(@PathVariable("course_id") Long course_id, @PathVariable("user_id") Long userId, @RequestBody Map<String, Boolean> payload){
        try{
            teacherService.updateAccess(course_id, userId, payload.get("access"));
            return new ResultUtil(200, null, "SUCCESS");
        }catch (Exception e){
            e.printStackTrace();
            return new ResultUtil(500, null, "FAIL");
        }
    }
}
