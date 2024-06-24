package com.swen90014.paplatypusbackend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swen90014.paplatypusbackend.domain.Student;
import com.swen90014.paplatypusbackend.dto.StudentDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentDao extends BaseMapper<Student> {
    @Select("SELECT * FROM Student WHERE name = #{name}")
    List<Student> getStudentByStudentName(String name);

    @Select("SELECT * FROM Student WHERE sis_user_id = #{sis_user_id}")
    List<Student> getStudentByStudentNumber(String sis_user_id);

    @Select("SELECT * FROM Student")
    List<Student> getAll();

    @Select("SELECT * FROM Student WHERE login_id = #{login_id}")
    List<Student> getStudentByUserLogin(String login_id);

    @Insert({
            "<script>",
            "INSERT INTO public.student (id, name, sis_user_id, login_id, type, course_id, email) VALUES ",
            "<foreach item='item' collection='list' separator=','>",
            "(#{item.id}, #{item.name}, #{item.sisUserId}, #{item.loginId}, #{item.type}, #{item.courseId}, #{item.email})",
            "</foreach>",
            "ON CONFLICT (id, type) DO UPDATE SET",
            "name = EXCLUDED.name, sis_user_id = EXCLUDED.sis_user_id, login_id = EXCLUDED.login_id, course_id = EXCLUDED.course_id, email = EXCLUDED.email",
            "</script>"
    })
    void insertOrUpdateBatch(@Param("list") List<Student> students);
    @Select("SELECT id FROM Student WHERE course_id = #{course_id}")
    List<Long> getStudentIdByCourseId(Long course_id);

    @Select("SELECT * FROM Student WHERE course_id = #{course_id}")
    List<Student> getStudentByCourseId(Long course_id);

    @Select("SELECT name FROM Student WHERE id = #{sid}")
    String getStudentNameById(Long sid);

    @Select("SELECT s.user_id, st.name, " +
            "SUM(CASE " +
            "WHEN COALESCE(a.method, a.default_method) = 'complete' THEN s.participation_score_complete " +
            "WHEN COALESCE(a.method, a.default_method) = 'weight' THEN s.participation_score_weight " +
            "ELSE 0 END) as student_total_score, " +
            "SUM(COALESCE(a.participation_weighting, a.default_participation_weighting))" +
            " as total_possible_score " +
            "FROM score s " +
            "JOIN assignment a ON s.assignment_id = a.id " +
            "JOIN student st ON s.user_id = st.id " +
            "WHERE a.course_id = #{courseId} " +
            "GROUP BY s.user_id, st.name")
    @Results({
            @Result(property = "id", column = "user_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "studentTotal", column = "student_total_score"),
            @Result(property = "totalPossible", column = "total_possible_score"),
    })
    List<StudentDTO> findStudentScoresByCourseId(Long courseId);


}
