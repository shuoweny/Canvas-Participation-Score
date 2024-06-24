package com.swen90014.paplatypusbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swen90014.paplatypusbackend.dao.AssignmentDao;
import com.swen90014.paplatypusbackend.domain.Assignment;
import com.swen90014.paplatypusbackend.service.IAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AssignmentServiceImpl extends ServiceImpl<AssignmentDao, Assignment> implements IAssignmentService {
    private final AssignmentDao assignmentDao;
    private final CourseServiceImpl courseService;
    private final ScoreServiceImpl scoreService;

    @Autowired
    public AssignmentServiceImpl(AssignmentDao assignmentDao, CourseServiceImpl courseService, ScoreServiceImpl scoreService) {
        this.assignmentDao = assignmentDao;
        this.courseService = courseService;
        this.scoreService = scoreService;
    }

    @Override
    public void saveAllAssignment(List<Assignment> assignments) {
        assignmentDao.insertOrUpdateBatch(assignments);
    }

    @Override
    public List<Assignment> getAssignmentByCourseId(Long course_id) {
        return assignmentDao.getAssignmentByCourseId(course_id);
    }
    public void updateWeights(Long courseId, List<Map<String, Object>> weights) {
        String method;
        String type;
        Integer weight;

        for (Map<String, Object> weightData : weights) {
            if (weightData.get("mode").equals("group")){
                type = (String) weightData.get("type");
                weight = (Integer) weightData.get("weight");
                method = (String) weightData.get("method");
                assignmentDao.updateWeightByTypeAndCourseId(type, weight, courseId, method);
            }
            if (weightData.get("mode").equals("single")){
                Long assignmentId = Long.valueOf((Integer) weightData.get("assignmentId"));
                weight = (Integer) weightData.get("weight");
                method = (String) weightData.get("method");
                System.out.println("count");
                assignmentDao.updateIndividualWeightByAssignmentIdAndCourseId(courseId, assignmentId, weight, method);
            }
        }
        scoreService.setParticipationScoreByScore(courseId);
    }


    @Override
    public List<String> getSortedAssignmentByCourseID(Long course_id) {
        return assignmentDao.getAssignmentByCourseIdSorted(course_id);
    }
}
