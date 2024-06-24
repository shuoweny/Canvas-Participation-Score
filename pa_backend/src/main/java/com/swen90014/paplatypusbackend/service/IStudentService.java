package com.swen90014.paplatypusbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.swen90014.paplatypusbackend.domain.Student;
import com.swen90014.paplatypusbackend.dto.StudentDTO;

import java.util.List;

public interface IStudentService extends IService<Student> {
    void saveAllStudent(List<Student> students);
    List<Student> getStudentByStudentName(String name);
    List<Student> getStudentByStudentNumber(String sis_user_id);
    List<Student> getStudentByUserLogin(String login_id);
    List<Long> getStudentIdByCourseId(Long course_id);
    List<Student> getStudentByCourseId(Long course_id);
    String getStudentNameById(Long sid);
    List<StudentDTO> findStudentScoresByCourseId(Long courseId);
}
