package com.swen90014.paplatypusbackend.controller;

import com.swen90014.paplatypusbackend.controller.utils.ResultUtil;
import com.swen90014.paplatypusbackend.domain.Assignment;
import com.swen90014.paplatypusbackend.service.impl.AssignmentServiceImpl;
import com.swen90014.paplatypusbackend.service.impl.ScoreServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/assignment")
public class AssignmentController {
    private final AssignmentServiceImpl assignmentService;
    private final ScoreServiceImpl scoreService;

    public AssignmentController(AssignmentServiceImpl assignmentService, ScoreServiceImpl scoreService) {
        this.assignmentService = assignmentService;
        this.scoreService = scoreService;
    }

    @GetMapping("/getAll")
    public ResultUtil getAll(){
        List<Assignment> assignmentList = assignmentService.list();
        int state = assignmentList.isEmpty()?500:200;
        String message = assignmentList.isEmpty()?"FAIL":"SUCCESS";
        return new ResultUtil(state, assignmentList, message);
    }
    @GetMapping("/getAssignmentById/{id}")
    public ResultUtil getById(@PathVariable Long id){
        Assignment assignment = assignmentService.getById(id);
        int state = assignment==null?500:200;
        String message = assignment==null?"FAIL":"SUCCESS";
        return new ResultUtil(state, assignment, message);
    }
    @GetMapping("/getAssignmentByCourseId/{course_id}")
    public ResultUtil getAssignmentByCourseId(@PathVariable Long course_id){
        List<Assignment> assignmentList = assignmentService.getAssignmentByCourseId(course_id);
        int state = assignmentList.isEmpty()?500:200;
        String message = assignmentList.isEmpty()?"FAIL":"SUCCESS";
        return new ResultUtil(state, assignmentList, message);
    }
    @PostMapping("/changeWeight/{course_id}")
    public ResultUtil changeWeight(@PathVariable("course_id") Long courseId, @RequestBody Map<String, Object> payload) {
        try {
            List<Map<String, Object>> weights = (List<Map<String, Object>>) payload.get("weights");
            assignmentService.updateWeights(courseId, weights);
            scoreService.setParticipationScoreByScore(courseId);
            return new ResultUtil(200, null, "SUCCESS");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultUtil(500, null, "FAIL");
        }
    }

}
