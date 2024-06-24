package com.swen90014.paplatypusbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.swen90014.paplatypusbackend.domain.Teacher;
import com.swen90014.paplatypusbackend.dto.AccessDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ITeacherService extends IService<Teacher> {
    void saveAllTeacher(List<Teacher> teachers);
    List<Teacher> getTeacherByName(String name);
    List<Teacher> getTeacherByUserLogin(String login_id);
    List<Teacher> getTeacherByUserNumber(String sis_user_id);
    List<AccessDTO> getTeacherAccessById(Long user_id);
    List<Teacher> getTeacherByCourseId(Long course_id);
    void updateAccess(Long courseId, Long userId, boolean access);
}
