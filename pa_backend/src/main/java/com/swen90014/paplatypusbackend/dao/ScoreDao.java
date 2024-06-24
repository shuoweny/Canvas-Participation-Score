package com.swen90014.paplatypusbackend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swen90014.paplatypusbackend.domain.Score;
import com.swen90014.paplatypusbackend.dto.*;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface ScoreDao extends BaseMapper<Score> {
    @Select("SELECT s.*, a.name, a.type, COALESCE(a.method, a.default_method) as method " +
            "FROM Score s " +
            "JOIN Assignment a ON s.assignment_id = a.id " +
            "WHERE s.assignment_id = #{assignment_id} AND s.course_id = #{course_id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "type", column = "type"),
            @Result(property = "participationScoreWeight", column = "participation_score_weight"),
            @Result(property = "participationScoreComplete", column = "participation_score_complete"),
            @Result(property = "assignmentId", column = "assignment_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "score", column = "score"),
            @Result(property = "method", column = "method")
    })
    List<ParticipationWeightDTO> getScoreForGivenAssignmentId(@Param("assignment_id") Long assignment_id, @Param("course_id") Long course_id);

    @Select("SELECT s.*, a.name, a.type, COALESCE(a.method, a.default_method) as method " +
            "FROM Score s " +
            "JOIN Assignment a ON s.assignment_id = a.id " +
            "WHERE user_id = #{user_id} AND s.course_id = #{course_id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "type", column = "type"),
            @Result(property = "participationScoreWeight", column = "participation_score_weight"),
            @Result(property = "participationScoreComplete", column = "participation_score_complete"),
            @Result(property = "assignmentId", column = "assignment_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "score", column = "score"),
            @Result(property = "method", column = "method")
    })
    List<ParticipationWeightDTO> getScoreForGivenUserId(@Param("user_id") Long user_id, @Param("course_id") Long course_id);

    @Select("SELECT s.*, a.name, a.type, COALESCE(a.method, a.default_method) as method " +
            "FROM Score s " +
            "JOIN Assignment a ON s.assignment_id = a.id " +
            "WHERE user_id = #{user_id} AND assignment_id = #{assignment_id} AND s.course_id = #{course_id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "type", column = "type"),
            @Result(property = "participationScoreWeight", column = "participation_score_weight"),
            @Result(property = "participationScoreComplete", column = "participation_score_complete"),
            @Result(property = "assignmentId", column = "assignment_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "score", column = "score"),
            @Result(property = "method", column = "method")
    })
    List<ParticipationWeightDTO> getOneScoreForGivenAssignmentId(@Param("user_id") Long user_id, @Param("assignment_id") Long assignment_id, @Param("course_id") Long course_id);

    @Select("SELECT s.*, a.name, a.type, COALESCE(a.method, a.default_method) as method " +
            "FROM Score s " +
            "JOIN Assignment a ON s.assignment_id = a.id " +
            "WHERE s.course_id = #{course_id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "type", column = "type"),
            @Result(property = "participationScoreWeight", column = "participation_score_weight"),
            @Result(property = "participationScoreComplete", column = "participation_score_complete"),
            @Result(property = "assignmentId", column = "assignment_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "score", column = "score"),
            @Result(property = "method", column = "method")
    })
    List<ParticipationWeightDTO> getScoreForGivenCourseId(Long course_id);

    @Insert({
            "<script>",
            "INSERT INTO Score (id, score, assignment_id, user_id, submitted_at, excused, late, missing, entered_score, course_id) VALUES ",
            "<foreach item='item' collection='list' separator=','>",
            "(#{item.id}, #{item.score}, #{item.assignmentId}, #{item.userId}, #{item.submittedAt}, #{item.excused}, #{item.late}, #{item.missing}, #{item.enteredScore}, #{item.courseId})",
            "</foreach>",
            "ON CONFLICT (id) DO UPDATE SET",
            "score = EXCLUDED.score, assignment_id = EXCLUDED.assignment_id, user_id = EXCLUDED.user_id, submitted_at = EXCLUDED.submitted_at, excused = EXCLUDED.excused, late = EXCLUDED.late, missing = EXCLUDED.missing, entered_score = EXCLUDED.entered_score, course_id = EXCLUDED.course_id",
            "</script>"
    })
    void insertOrUpdateBatch(@Param("list") List<Score> scores);
    @Select("SELECT a.type as assignment_type, " +
            "s.participation_score_weight, " +
            "s.participation_score_complete, " +
            "a.name as assignment_name, " +
            "c.name as course_name, " +
            "u.name as student_name, " +
            "s.score, " +
            "u.email " +
            " FROM Score s " +
            " JOIN Assignment a ON s.assignment_id = a.id " +
            " JOIN Course c ON s.course_id = c.id " +
            " JOIN Student u ON s.user_id = u.id " +
            " WHERE s.user_id = #{user_id} AND s.course_id = #{course_id}")
    @Results({
            @Result(property = "type", column = "assignment_type"),
            @Result(property = "participationScoreWeight", column = "participation_score_weight"),
            @Result(property = "participationScoreComplete", column = "participation_score_complete"),
            @Result(property = "assignmentName", column = "assignment_name"),
            @Result(property = "courseName", column = "course_name"),
            @Result(property = "studentName", column = "student_name"),
            @Result(property = "score", column = "score")
    })
    List<StudentPdfDTO> getScorePdfForGivenUserId(@Param("user_id") Long user_id, @Param("course_id") Long course_id);

    @Select("SELECT ROUND(AVG(CASE " +
            "WHEN s.participation_score_weight IS NULL AND a.default_participation_weighting IS NULL" +
            " THEN a.default_participation_weighting" +
            " WHEN s.participation_score_weight IS NULL" +
            " THEN s.participation_score_complete" +
            " ELSE s.participation_score_weight END), 2) AS average_participation_score_weight" +
            " FROM score s" +
            " JOIN assignment a ON s.assignment_id = a.id" +
            " WHERE a.course_id = #{course_id}")
    BigDecimal getAverageParticipationTotal(@Param("course_id") Long course_id);


    @Select("SELECT ROUND(AVG(CASE " +
            "WHEN s.participation_score_weight IS NOT NULL THEN s.participation_score_weight " +
            "WHEN s.participation_score_weight IS NULL AND s.participation_score_complete IS NOT NULL THEN s.participation_score_complete " +
            "ELSE a.default_participation_weighting END), 2) AS average_participation_score_weight " +
            "FROM score s " +
            "JOIN assignment a ON s.assignment_id = a.id " +
            "WHERE a.course_id = #{course_id} " +
            "AND s.submitted_at >= (NOW() - INTERVAL '1 week')")
    BigDecimal getAverageParticipationLastWeek(@Param("course_id") Long course_id);


    @Select("SELECT" +
            " assignment.id AS assignment_id," +
            "assignment.name AS assignment_name," +
            " ROUND(AVG(CASE" +
            " WHEN score.participation_score_weight IS NULL AND assignment.default_participation_weighting IS NULL" +
            " THEN assignment.default_participation_weighting" +
            " WHEN score.participation_score_weight IS NULL" +
            " THEN score.participation_score_complete" +
            " ELSE score.participation_score_weight" +
            " END),2) AS average_participation_score_weight" +
            " FROM assignment" +
            " JOIN score ON assignment.id = score.assignment_id" +
            " GROUP BY assignment.id, assignment.name" +
            " ORDER BY average_participation_score_weight DESC" +
            " LIMIT 1")
    @Results({
            @Result(property = "name", column = "assignment_name"),
            @Result(property = "score", column = "average_participation_score_weight"),
    })
    SubjectOverviewDTO getHighestParticipationActivity(@Param("course_id") Long course_id);

    @Select("SELECT a.type AS assignment_type," +
            "ROUND(AVG(CASE" +
            " WHEN s.participation_score_weight IS NULL AND a.default_participation_weighting IS NULL" +
            " THEN a.default_participation_weighting" +
            " WHEN s.participation_score_weight IS NULL" +
            " THEN s.participation_score_complete" +
            " ELSE s.participation_score_weight END), 2) AS average_participation_score_weight" +
            " FROM assignment a" +
            " JOIN score s ON a.id = s.assignment_id" +
            " GROUP BY a.type" +
            " ORDER BY average_participation_score_weight DESC" +
            " LIMIT 1")
    @Results({
            @Result(property = "name", column = "assignment_type"),
            @Result(property = "score", column = "average_participation_score_weight"),
    })
    SubjectOverviewDTO getHighestParticipationActivityType(@Param("course_id") Long course_id);

    @Select("SELECT" +
            " assignment.id AS assignment_id," +
            "assignment.name AS assignment_name," +
            "ROUND(AVG(CASE" +
            " WHEN score.participation_score_weight IS NULL AND assignment.default_participation_weighting IS NULL" +
            " THEN assignment.default_participation_weighting" +
            " WHEN score.participation_score_weight IS NULL" +
            " THEN score.participation_score_complete" +
            " ELSE score.participation_score_weight" +
            " END), 2) AS average_participation_score_weight" +
            " FROM assignment" +
            " JOIN score ON assignment.id = score.assignment_id" +
            " GROUP BY assignment.id, assignment.name" +
            " ORDER BY average_participation_score_weight ASC" +
            " LIMIT 1")
    @Results({
            @Result(property = "name", column = "assignment_name"),
            @Result(property = "score", column = "average_participation_score_weight"),
    })
    SubjectOverviewDTO getLowestParticipationActivity(@Param("course_id") Long course_id);

    @Select("SELECT a.type AS assignment_type," +
            "ROUND(AVG(CASE" +
            " WHEN s.participation_score_weight IS NULL AND a.default_participation_weighting IS NULL" +
            " THEN a.default_participation_weighting" +
            " WHEN s.participation_score_weight IS NULL" +
            " THEN s.participation_score_complete" +
            " ELSE s.participation_score_weight END), 2) AS average_participation_score_weight" +
            " FROM assignment a" +
            " JOIN score s ON a.id = s.assignment_id" +
            " GROUP BY a.type" +
            " ORDER BY average_participation_score_weight ASC" +
            " LIMIT 1")
    @Results({
            @Result(property = "name", column = "assignment_type"),
            @Result(property = "score", column = "average_participation_score_weight"),
    })
    SubjectOverviewDTO getLowestParticipationActivityType(@Param("course_id") Long course_id);

    void setParticipationScoreByComplete(Long courseId);

    void setParticipationScoreByScore(Long courseId);

    List<ExcelDTO> getExcelDTO(Long courseId);
    List<PersonalChartDTO> getPersonChartData(Long courseId, Long studentId);

    List<ChartDTO> getSubjectChartData(Long courseId);
}
