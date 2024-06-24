package com.swen90014.paplatypusbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.swen90014.paplatypusbackend.domain.Score;
import com.swen90014.paplatypusbackend.dto.SubjectOverviewDTO;

import java.math.BigDecimal;

// import java.util.List;

public interface IOverviewService extends IService<Score> {
    BigDecimal getAverageParticipationTotal(Long courseId);

    BigDecimal getAverageParticipationLastWeek(Long courseId);

    SubjectOverviewDTO getHighestParticipationActivity(Long courseId);

    SubjectOverviewDTO getHighestParticipationActivityType(Long courseId);

    SubjectOverviewDTO getLowestParticipationActivity(Long courseId);

    SubjectOverviewDTO getLowestParticipationActivityType(Long courseId);

}
