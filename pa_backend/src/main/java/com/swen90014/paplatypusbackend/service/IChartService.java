package com.swen90014.paplatypusbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.swen90014.paplatypusbackend.controller.utils.ChartUtil;
import com.swen90014.paplatypusbackend.controller.utils.barChartUtil;
import com.swen90014.paplatypusbackend.domain.Score;

import java.awt.image.BufferedImage;
import java.util.Map;

public interface IChartService extends IService<Score> {
    BufferedImage studentChart(Long subjectId, Long studentId);

    BufferedImage subjectChart(Long subjectId);

    barChartUtil barData(Long subjectId);

    ChartUtil pieData(Long subjectId, Long studentId);
}
