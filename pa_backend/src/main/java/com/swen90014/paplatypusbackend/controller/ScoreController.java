package com.swen90014.paplatypusbackend.controller;

import com.swen90014.paplatypusbackend.controller.utils.ResultUtil;
import com.swen90014.paplatypusbackend.domain.Score;
import com.swen90014.paplatypusbackend.dto.ParticipationWeightDTO;
import com.swen90014.paplatypusbackend.service.IScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/score")
public class ScoreController {
    @Autowired
    private IScoreService iScoreService;

    @GetMapping("/getAll")
    public ResultUtil getAll(){
        List<Score> scoreList = iScoreService.list();
        int state = scoreList.isEmpty()?500:200;
        String message = scoreList.isEmpty()?"FAIL":"SUCCESS";
        return new ResultUtil(state, scoreList, message);
    }

    @GetMapping("/getByScoreId/{id}")
    public ResultUtil getByScoreId(@PathVariable Long id){
        Score score = iScoreService.getById(id);
        int state = score!=null?200:500;
        String message = score!=null?"SUCCESS":"FAIL";
        return new ResultUtil(state, score, message);
    }
    @GetMapping("/getScoreForGivenUserId/{course_id}/{user_id}")
    public ResultUtil getScoreForGivenUserId(@PathVariable("course_id") Long course_id, @PathVariable("user_id") Long user_id){
        List<ParticipationWeightDTO> scoreList = iScoreService.getScoreForGivenUserId(user_id, course_id);
        int state = scoreList.isEmpty()?500:200;
        String message = scoreList.isEmpty()?"FAIL":"SUCCESS";
        return new ResultUtil(state, scoreList, message);
    }

    @GetMapping("/getScoreForGivenAssignmentId/{course_id}/{assignment_id}")
    public ResultUtil getScoreForGivenAssignmentId(@PathVariable("course_id") Long course_id, @PathVariable Long assignment_id){
        List<ParticipationWeightDTO> scoreList = iScoreService.getScoreForGivenAssignmentId(assignment_id, course_id);
        int state = scoreList.isEmpty()?500:200;
        String message = scoreList.isEmpty()?"FAIL":"SUCCESS";
        return new ResultUtil(state, scoreList, message);
    }
    @GetMapping("/getOneScoreForGivenAssignmentId/{course_id}/{assignment_id}/{user_id}")
    public ResultUtil getOneScoreForGivenAssignmentId(@PathVariable("course_id") Long course_id, @PathVariable Long assignment_id, @PathVariable Long user_id){
        List<ParticipationWeightDTO> scoreList = iScoreService.getOneScoreForGivenAssignmentId(user_id, assignment_id, course_id);
        int state = scoreList.isEmpty()?500:200;
        String message = scoreList.isEmpty()?"FAIL":"SUCCESS";
        return new ResultUtil(state, scoreList, message);
    }
    @GetMapping("/getScoreForGivenCourseId/{course_id}")
    public ResultUtil getScoreForGivenCourseId(@PathVariable("course_id") Long course_id){
        List<ParticipationWeightDTO> scoreList = iScoreService.getOneScoreForGivenCourseId(course_id);
        int state = scoreList.isEmpty()?500:200;
        String message = scoreList.isEmpty()?"FAIL":"SUCCESS";
        return new ResultUtil(state, scoreList, message);
    }
}
