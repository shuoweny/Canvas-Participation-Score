package com.swen90014.paplatypusbackend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swen90014.paplatypusbackend.domain.Course;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CourseDao extends BaseMapper<Course> {
    @Select("SELECT * FROM Course WHERE name = #{name}")
    List<Course> getCourseByCourseName(String name);

    @Select("SELECT * FROM Course WHERE course_code = #{course_code}")
    List<Course> getCourseByCourseCode(String course_code);
    @Insert({
            "<script>",
            "INSERT INTO Course (id, name, course_code, user_id) VALUES ",
            "<foreach item='item' collection='list' separator=','>",
            "(#{item.id}, #{item.name}, #{item.courseCode}, #{item.userId})",
            "</foreach>",
            "ON CONFLICT (id, user_id) DO UPDATE SET",
            "name = EXCLUDED.name, course_code = EXCLUDED.course_code, user_id = EXCLUDED.user_id",
            "</script>"
    })
    void insertOrUpdateBatch(@Param("list") List<Course> courses);
    @Select("SELECT id FROM Course")
    List<Long> selectAllIds();

    @Select("SELECT * FROM Course WHERE user_id = #{userId}")
    List<Course> selectCourseByUserId(Long userId);
}
