package com.swen90014.paplatypusbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.swen90014.paplatypusbackend.domain.Score;
import com.swen90014.paplatypusbackend.dto.EasyExcelDTO;
import com.swen90014.paplatypusbackend.dto.ParticipationWeightDTO;
import com.swen90014.paplatypusbackend.dto.StudentPdfDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IScoreService extends IService<Score> {
//    List<Score> getScoreByStudentId(Long sid);
//
//    List<Score> getScoreByActivityId(Long aid);
//
//    Score getOneScoreById(Long sid, Long aid);
    void saveAllScore(List<Score> scores);
    List<ParticipationWeightDTO> getScoreForGivenAssignmentId(Long assignment_id, Long course_id);
    List<ParticipationWeightDTO> getScoreForGivenUserId(Long user_id, Long course_id);
    List<ParticipationWeightDTO> getOneScoreForGivenAssignmentId(Long user_id, Long assignment_id, Long course_id);
    void setParticipationScoreByComplete(Long courseId);
    void setParticipationScoreByScore(Long courseId);
    List<ParticipationWeightDTO> getOneScoreForGivenCourseId(Long course_id);
    List<StudentPdfDTO> getScorePdfForGivenUserId(@Param("user_id") Long user_id, @Param("course_id") Long course_id);
    List<EasyExcelDTO> fetchDataForExcel(Long course_id);
}
