package com.swen90014.paplatypusbackend.controller;

import com.swen90014.paplatypusbackend.controller.utils.ResultUtil;
import com.swen90014.paplatypusbackend.domain.Score;
import com.swen90014.paplatypusbackend.dto.SubjectOverviewDTO;
import com.swen90014.paplatypusbackend.service.IOverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/overview")
public class OverviewController {
    @Autowired
    private IOverviewService iOverviewService;

    @GetMapping("/getAverageParticipationTotal/{course_id}")
    public ResultUtil getAverageParticipationTotal(@PathVariable("course_id") Long course_id){
        BigDecimal averageParticipationTotal = iOverviewService.getAverageParticipationTotal(course_id);
        int state = averageParticipationTotal!=null?200:500;
        String message = averageParticipationTotal!=null?"SUCCESS":"FAIL";
        return new ResultUtil(state, averageParticipationTotal, message);
    }

    @GetMapping("/getAverageParticipationLastWeek/{course_id}")
    public ResultUtil getAverageParticipationLastWeek(@PathVariable("course_id") Long course_id){
        BigDecimal averageParticipationLastWeek = iOverviewService.getAverageParticipationLastWeek(course_id);
        int state = averageParticipationLastWeek!=null?200:500;
        String message = averageParticipationLastWeek!=null?"SUCCESS":"FAIL";
        return new ResultUtil(state, averageParticipationLastWeek, message);
    }

    @GetMapping("/getHighestParticipationActivity/{course_id}")
    public ResultUtil getHighestParticipationActivity(@PathVariable("course_id") Long course_id){
        SubjectOverviewDTO highestParticipationActivity = iOverviewService.getHighestParticipationActivity(course_id);
        int state = highestParticipationActivity!=null?200:500;
        String message = highestParticipationActivity!=null?"SUCCESS":"FAIL";
        return new ResultUtil(state, highestParticipationActivity, message);
    }

    @GetMapping("/getHighestParticipationActivityType/{course_id}")
    public ResultUtil getHighestParticipationActivityType(@PathVariable("course_id") Long course_id){
        SubjectOverviewDTO highestParticipationActivityType = iOverviewService.getHighestParticipationActivityType(course_id);
        int state = highestParticipationActivityType!=null?200:500;
        String message = highestParticipationActivityType!=null?"SUCCESS":"FAIL";
        return new ResultUtil(state, highestParticipationActivityType, message);
    }

    @GetMapping("/getLowestParticipationActivity/{course_id}")
    public ResultUtil getLowestParticipationActivity(@PathVariable("course_id") Long course_id){
        SubjectOverviewDTO lowestParticipationActivity = iOverviewService.getLowestParticipationActivity(course_id);
        int state = lowestParticipationActivity!=null?200:500;
        String message = lowestParticipationActivity!=null?"SUCCESS":"FAIL";
        return new ResultUtil(state, lowestParticipationActivity, message);
    }

    @GetMapping("/getLowestParticipationActivityType/{course_id}")
    public ResultUtil getLowestParticipationActivityType(@PathVariable("course_id") Long course_id){
        SubjectOverviewDTO lowestParticipationActivityType = iOverviewService.getLowestParticipationActivityType(course_id);
        int state = lowestParticipationActivityType!=null?200:500;
        String message = lowestParticipationActivityType!=null?"SUCCESS":"FAIL";
        return new ResultUtil(state, lowestParticipationActivityType, message);
    }
}
