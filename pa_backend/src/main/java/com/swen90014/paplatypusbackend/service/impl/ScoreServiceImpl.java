
package com.swen90014.paplatypusbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swen90014.paplatypusbackend.dao.AssignmentDao;
import com.swen90014.paplatypusbackend.dao.ScoreDao;
import com.swen90014.paplatypusbackend.dao.StudentDao;
import com.swen90014.paplatypusbackend.domain.Assignment;
import com.swen90014.paplatypusbackend.domain.Score;
import com.swen90014.paplatypusbackend.domain.Student;
import com.swen90014.paplatypusbackend.domain.User;
import com.swen90014.paplatypusbackend.dto.EasyExcelDTO;
import com.swen90014.paplatypusbackend.dto.ExcelDTO;
import com.swen90014.paplatypusbackend.dto.ParticipationWeightDTO;
import com.swen90014.paplatypusbackend.dto.StudentPdfDTO;
import com.swen90014.paplatypusbackend.service.IScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class ScoreServiceImpl extends ServiceImpl<ScoreDao, Score> implements IScoreService {
    private final ScoreDao scoreDao;
    private final StudentDao studentDao;
    private final AssignmentDao assignmentDao;
    @Autowired
    public ScoreServiceImpl(ScoreDao scoreDao, AssignmentDao assignmentDao, StudentDao studentDao) {
        this.scoreDao = scoreDao;
        this.assignmentDao  = assignmentDao;
        this.studentDao = studentDao;
    }

    @Override
    public void saveAllScore(List<Score> scores) {
        scoreDao.insertOrUpdateBatch(scores);
    }

    @Override
    public List<ParticipationWeightDTO> getScoreForGivenAssignmentId(Long assignment_id, Long course_id) {
        return scoreDao.getScoreForGivenAssignmentId(assignment_id, course_id);
    }

    @Override
    public List<ParticipationWeightDTO> getScoreForGivenUserId(Long user_id, Long course_id) {
        return scoreDao.getScoreForGivenUserId(user_id, course_id);
    }

    @Override
    public List<ParticipationWeightDTO> getOneScoreForGivenAssignmentId(Long user_id, Long assignment_id, Long course_id) {
        return scoreDao.getOneScoreForGivenAssignmentId(user_id, assignment_id, course_id);
    }

    @Override
    public void setParticipationScoreByComplete(Long courseId) {
        scoreDao.setParticipationScoreByComplete(courseId);
    }

    @Override
    public void setParticipationScoreByScore(Long courseId) {
        scoreDao.setParticipationScoreByScore(courseId);
    }

    @Override
    public List<ParticipationWeightDTO> getOneScoreForGivenCourseId(Long course_id) {
        return scoreDao.getScoreForGivenCourseId(course_id);
    }


    @Override
    public List<StudentPdfDTO> getScorePdfForGivenUserId(Long user_id, Long course_id) {
        return scoreDao.getScorePdfForGivenUserId(user_id, course_id);
    }

    @Override
    public List<EasyExcelDTO> fetchDataForExcel(Long course_id) {
        List<ExcelDTO> excelList = scoreDao.getExcelDTO(course_id);
        List<EasyExcelDTO> res = new ArrayList<>();
        Map<Integer, Map<String, BigDecimal>> checker = new HashMap<>();// student_id ----  scoreList
        Map<Integer, String> nameList = new HashMap<>();
        for(ExcelDTO excelDTO : excelList){
            Integer student_id = excelDTO.getUserId();
            nameList.put(student_id, excelDTO.getStudentName());
            if(checker.get(student_id)==null){
                Map<String, BigDecimal> scoreList = new HashMap<>();
                checker.put(student_id, scoreList);
            }
            String ass = excelDTO.getAssignmentName();
            checker.get(student_id).put(ass, excelDTO.getParticipationScore());
        }
        Set<Integer> students = checker.keySet();
        for(Integer id : students){
            EasyExcelDTO easyExcelDTO = new EasyExcelDTO();
            easyExcelDTO.setStudentId(id);
            easyExcelDTO.setStudentName(nameList.get(id));
            easyExcelDTO.setAssignmentScores(checker.get(id));
            res.add(easyExcelDTO);
        }
        return res;
    }
}
