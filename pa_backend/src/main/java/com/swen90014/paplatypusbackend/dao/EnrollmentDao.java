package com.swen90014.paplatypusbackend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swen90014.paplatypusbackend.domain.Enrollment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EnrollmentDao extends BaseMapper<Enrollment> {
    @Select("SELECT * FROM Enrollment WHERE course_id = #{course_id}")
    List<Enrollment> getEnrollmentByCourseId(Long course_id);

    @Select("SELECT * FROM Enrollment WHERE user_id = #{user_id}")
    List<Enrollment> getEnrollmentByUserId(Long user_id);

    @Insert({
            "<script>",
            "INSERT INTO public.enrollment (type, user_id, course_id, enrollment_state) VALUES ",
            "<foreach item='item' collection='list' separator=','>",
            "(#{item.type}, #{item.userId}, #{item.courseId}, #{item.enrollmentState})",
            "</foreach>",
            "ON CONFLICT (course_id, user_id, type) DO UPDATE SET",
            "type = EXCLUDED.type, enrollment_state = EXCLUDED.enrollment_state",
            "</script>"
    })
    void insertOrUpdateBatch(@Param("list") List<Enrollment> enrollments);
}
