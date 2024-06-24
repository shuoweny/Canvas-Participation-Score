package com.swen90014.paplatypusbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.swen90014.paplatypusbackend.domain.Score;
import com.swen90014.paplatypusbackend.dto.ExcelDTO;

import java.util.List;

public interface IParticipationScoreService extends IService<Score> {
    void CalculateAndSaveByComplete(Long courseId);

    void CalculateAndSaveByScore(Long courseId);

    List<ExcelDTO> getExcelDTO(Long courseId);
}
