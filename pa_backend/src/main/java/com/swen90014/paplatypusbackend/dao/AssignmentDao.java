package com.swen90014.paplatypusbackend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swen90014.paplatypusbackend.domain.Assignment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AssignmentDao extends BaseMapper<Assignment> {
    @Select("SELECT * FROM Assignment WHERE course_id = #{course_id}")
    List<Assignment> getAssignmentByCourseId(Long course_id);

    @Select("SELECT name FROM Assignment WHERE course_id = #{course_id} ORDER BY type")
    List<String> getAssignmentByCourseIdSorted(Long course_id);
    @Insert({
            "<script>",
            "INSERT INTO Assignment (id, description, due_at, unlock_at, lock_at, points_possible, created_at, updated_at, published, course_id, name, default_participation_weighting, default_method, type) VALUES",
            "<foreach item='item' collection='list' separator=','>",
            "(",
            "#{item.id}, #{item.description}, #{item.dueAt}, #{item.unlockAt}, #{item.lockAt}, #{item.pointsPossible}, #{item.createdAt}, #{item.updatedAt}, #{item.published}, #{item.courseId}, #{item.name},",
            "COALESCE((SELECT default_participation_weighting FROM Assignment WHERE type = #{item.type} LIMIT 1), #{item.defaultParticipationWeighting}),",
            "COALESCE((SELECT default_method FROM Assignment WHERE type = #{item.type} LIMIT 1), #{item.defaultMethod}),",
            "#{item.type}",
            ")",
            "</foreach>",
            "ON CONFLICT (id) DO UPDATE SET",
            "description = EXCLUDED.description, due_at = EXCLUDED.due_at, unlock_at = EXCLUDED.unlock_at, lock_at = EXCLUDED.lock_at, points_possible = EXCLUDED.points_possible, created_at = EXCLUDED.created_at, updated_at = EXCLUDED.updated_at, published = EXCLUDED.published, course_id = EXCLUDED.course_id, name = EXCLUDED.name, default_participation_weighting = EXCLUDED.default_participation_weighting, default_method = EXCLUDED.default_method, type = EXCLUDED.type",
            "</script>"
    })
    void insertOrUpdateBatch(@Param("list") List<Assignment> assignments);

    @Update("UPDATE Assignment SET default_participation_weighting = #{weight}, default_method = #{method} WHERE type = #{type} AND course_id = #{courseId}")
    void updateWeightByTypeAndCourseId(@Param("type") String type, @Param("weight") Integer weight, @Param("courseId") Long courseId, @Param("method") String method);
    @Update("UPDATE Assignment SET participation_weighting = #{weight}, method = #{method} WHERE id = #{assignmentId} AND course_id = #{courseId}")
    void updateIndividualWeightByAssignmentIdAndCourseId(@Param("courseId") Long courseId, @Param("assignmentId") Long assignmentId, @Param("weight") Integer weight, @Param("method") String method);

}
