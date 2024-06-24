package com.swen90014.paplatypusbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swen90014.paplatypusbackend.dao.ScoreDao;
import com.swen90014.paplatypusbackend.domain.Score;
import com.swen90014.paplatypusbackend.dto.ExcelDTO;
import com.swen90014.paplatypusbackend.service.IParticipationScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipationScoreServiceImpl extends ServiceImpl<ScoreDao, Score> implements IParticipationScoreService {
    private final ScoreDao scoreDao;

    @Autowired
    public ParticipationScoreServiceImpl(ScoreDao scoreDao) {
        this.scoreDao = scoreDao;
    }

    @Override
    public void CalculateAndSaveByComplete(Long courseId) {
        scoreDao.setParticipationScoreByComplete(courseId);
    }

    @Override
    public void CalculateAndSaveByScore(Long courseId) {
        scoreDao.setParticipationScoreByScore(courseId);
    }

    @Override
    public List<ExcelDTO> getExcelDTO(Long courseId) {
        return scoreDao.getExcelDTO(courseId);
    }



}
