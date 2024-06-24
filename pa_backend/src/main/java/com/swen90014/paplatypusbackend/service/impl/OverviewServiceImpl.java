package com.swen90014.paplatypusbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swen90014.paplatypusbackend.dao.ScoreDao;
import com.swen90014.paplatypusbackend.domain.Score;
import com.swen90014.paplatypusbackend.dto.SubjectOverviewDTO;
import com.swen90014.paplatypusbackend.service.IOverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class OverviewServiceImpl extends ServiceImpl<ScoreDao, Score> implements IOverviewService {
    private final ScoreDao scoreDao;

    @Autowired
    public OverviewServiceImpl(ScoreDao scoreDao) {
        this.scoreDao = scoreDao;
    }

    @Override
    public BigDecimal getAverageParticipationTotal(Long courseId) {
        return scoreDao.getAverageParticipationTotal(courseId);
    }

    @Override
    public BigDecimal getAverageParticipationLastWeek(Long courseId) {
        return scoreDao.getAverageParticipationLastWeek(courseId);
    }

    @Override
    public SubjectOverviewDTO getHighestParticipationActivity(Long courseId) {
        return scoreDao.getHighestParticipationActivity(courseId);
    }

    @Override
    public SubjectOverviewDTO getHighestParticipationActivityType(Long courseId) {
        return scoreDao.getHighestParticipationActivityType(courseId);
    }

    @Override
    public SubjectOverviewDTO getLowestParticipationActivity(Long courseId) {
        return scoreDao.getLowestParticipationActivity(courseId);
    }

    @Override
    public SubjectOverviewDTO getLowestParticipationActivityType(Long courseId) {
        return scoreDao.getLowestParticipationActivityType(courseId);
    }

}