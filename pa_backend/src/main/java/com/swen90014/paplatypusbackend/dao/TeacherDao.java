package com.swen90014.paplatypusbackend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swen90014.paplatypusbackend.domain.Teacher;
import com.swen90014.paplatypusbackend.dto.AccessDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TeacherDao extends BaseMapper<Teacher> {
    @Select("SELECT * FROM Teacher WHERE name = #{name}")
    List<Teacher> getTeacherByName(String name);

    @Select("SELECT * FROM Teacher WHERE sis_user_id = #{sis_user_id}")
    List<Teacher> getTeacherByUserNumber(String sis_user_id);

    @Select("SELECT * FROM Teacher WHERE login_id = #{login_id}")
    List<Teacher> getTeacherByUserLogin(String login_id);
    @Select("SELECT * FROM Teacher WHERE course_id = #{course_id}")
    List<Teacher> getTeacherByCourseId(Long course_id);
    @Insert({
            "<script>",
            "INSERT INTO public.teacher (id, name, sis_user_id, login_id, type, course_id, email) VALUES ",
            "<foreach item='item' collection='list' separator=','>",
            "(#{item.id}, #{item.name}, #{item.sisUserId}, #{item.loginId}, #{item.type}, #{item.courseId}, #{item.email})",
            "</foreach>",
            "ON CONFLICT (id, type) DO UPDATE SET",
            "name = EXCLUDED.name, sis_user_id = EXCLUDED.sis_user_id, login_id = EXCLUDED.login_id, course_id = EXCLUDED.course_id, email = EXCLUDED.email",
            "</script>"
    })
    void insertOrUpdateBatch(@Param("list") List<Teacher> teachers);
    @Select("SELECT t.access, c.name, t.type FROM Teacher t JOIN Course c ON t.id = c.user_id WHERE user_id = #{user_id}")
    @Results({
            @Result(property = "access", column = "access"),
            @Result(property = "name", column = "name"),
            @Result(property = "type", column = "type")
    })
    List<AccessDTO> getTeacherAccessById(Long user_id);
    @Update("UPDATE teacher SET access = #{access} WHERE course_id = #{course_id} AND id = #{id}")
    void updateAccess(@Param("course_id") Long courseId, @Param("id") Long userId, @Param("access") boolean access);


}
