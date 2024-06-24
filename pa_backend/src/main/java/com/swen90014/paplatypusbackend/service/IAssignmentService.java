package com.swen90014.paplatypusbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.swen90014.paplatypusbackend.domain.Assignment;

import java.util.List;
import java.util.Map;

public interface IAssignmentService extends IService<Assignment> {
    void saveAllAssignment(List<Assignment> assignments);
    List<Assignment> getAssignmentByCourseId(Long course_id);

    List<String> getSortedAssignmentByCourseID(Long course_id);
    void updateWeights(Long courseId, List<Map<String, Object>> weights);
}
