package com.swen90014.paplatypusbackend.controller;

import com.swen90014.paplatypusbackend.controller.utils.ResultUtil;
import com.swen90014.paplatypusbackend.domain.Teacher;
import com.swen90014.paplatypusbackend.domain.User;
import com.swen90014.paplatypusbackend.service.impl.TeacherServiceImpl;
import com.swen90014.paplatypusbackend.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;
    @GetMapping("/getAll")
    public ResultUtil getAll(){
        List<User> userList = userService.list();
        int state = userList.isEmpty()?500:200;
        String message = userList.isEmpty()?"FAIL":"SUCCESS";
        return new ResultUtil(state, userList, message);
    }
    @GetMapping("/getUserById/{id}")
    public ResultUtil getById(@PathVariable Long id){
        User user = userService.getById(id);
        int state = user==null?500:200;
        String message = user==null?"FAIL":"SUCCESS";
        return new ResultUtil(state, user, message);
    }
    @GetMapping("/getUserByToken/{token}")
    public ResultUtil getUserByToken(@PathVariable String token){
        User user = userService.getUserByToken(token);
        int state = user==null?500:200;
        String message = user==null?"FAIL":"SUCCESS";
        return new ResultUtil(state, user, message);
    }
    @GetMapping("/getUserByName/{name}")
    public ResultUtil getUserByName(@PathVariable String name){
        List<User> userList = userService.getUserByName(name);
        int state = userList.isEmpty()?500:200;
        String message = userList.isEmpty()?"FAIL":"SUCCESS";
        return new ResultUtil(state, userList, message);
    }

}
