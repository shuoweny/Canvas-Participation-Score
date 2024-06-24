package com.swen90014.paplatypusbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swen90014.paplatypusbackend.controller.utils.ChartUtil;
import com.swen90014.paplatypusbackend.controller.utils.barChartDetailUtil;
import com.swen90014.paplatypusbackend.controller.utils.barChartUtil;
import com.swen90014.paplatypusbackend.dao.ScoreDao;
import com.swen90014.paplatypusbackend.domain.Score;
import com.swen90014.paplatypusbackend.dto.ChartDTO;
import com.swen90014.paplatypusbackend.dto.PersonalChartDTO;
import com.swen90014.paplatypusbackend.service.IChartService;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;


@Service
public class ChartServiceImpl extends ServiceImpl<ScoreDao, Score> implements IChartService {
    private final ScoreDao scoreDao;

    @Autowired
    public ChartServiceImpl(ScoreDao scoreDao) {
        this.scoreDao = scoreDao;
    }



    @Override
    public BufferedImage studentChart(Long subjectId, Long studentId) {

        List<PersonalChartDTO> completedList = scoreDao.getPersonChartData(subjectId, studentId);
        int completed = 0;
        int notButCan = 0;
        int cannot = 0;
        for (PersonalChartDTO i : completedList) {
            if (i.getCompleteState() == 0) {
                completed++;
            } else if (i.getCompleteState() == 1) {
                notButCan++;
            } else {
                cannot++;
            }
        }

        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Done", completed);
        dataset.setValue("Catching Up", notButCan);
        dataset.setValue("Not Done", cannot);

        JFreeChart pieChart = ChartFactory.createPieChart(
                "Personal Participation Chart",
                dataset,
                true,
                true,
                false
        );
        PiePlot plot = (PiePlot) pieChart.getPlot();
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} = {1} ({2})"));
        BufferedImage image = pieChart.createBufferedImage(1500, 1500);
        return image;
    }

    @Override
    public BufferedImage subjectChart(Long subjectId) {
        List<ChartDTO> complete_list = scoreDao.getSubjectChartData(subjectId);
        HashMap<String, int[]> chartMap = new HashMap<>();
        for(ChartDTO chartDTO: complete_list){
            int[] list = chartMap.getOrDefault(chartDTO.getName(), new int[3]);
            if (chartDTO.getCompelete_state() == 0) {
                list[0]++;
            } else if (chartDTO.getCompelete_state() == 1) {
                list[1]++;
            } else {
                list[2]++;
            }
            chartMap.put(chartDTO.getName(), list);
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Set<String> assignments = chartMap.keySet();
        for (String ass : assignments) {
            dataset.addValue(chartMap.get(ass)[0], "Done", ass);
            dataset.addValue(chartMap.get(ass)[1], "Catching Up", ass);
            dataset.addValue(chartMap.get(ass)[2], "Not Done", ass);
        }

        JFreeChart stackedBarChart = ChartFactory.createStackedBarChart(
                "Subject Chart",
                "Assignments",
                "Count",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        CategoryPlot plot = (CategoryPlot) stackedBarChart.getPlot();
        CategoryAxis xAxis = plot.getDomainAxis();
        xAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 4.0));
        BufferedImage image = stackedBarChart.createBufferedImage(1500, 1500);
        return image;
    }

    @Override
    public barChartUtil barData(Long subjectId) {
        List<ChartDTO> complete_list = scoreDao.getSubjectChartData(subjectId);
        HashMap<String, ChartUtil> chartMap = new HashMap<>();
        for(ChartDTO chartDTO: complete_list){
            ChartUtil chartUtil = chartMap.getOrDefault(chartDTO.getName(), new ChartUtil());
            if (chartDTO.getCompelete_state() == 0) {
                chartUtil.setCompleted(chartUtil.getCompleted()+1);
            } else if (chartDTO.getCompelete_state() == 1) {
                chartUtil.setNotYetCompleted(chartUtil.getNotYetCompleted()+1);
            } else {
                chartUtil.setOverDue(chartUtil.getOverDue()+1);
            }
            chartMap.put(chartDTO.getName(), chartUtil);
        }
        barChartUtil barChartUtil = new barChartUtil();

        Set<String> assignments = chartMap.keySet();
        int[] Completed = new int[chartMap.size()];
        int[] NotYetCompleted = new int[chartMap.size()];
        int[] OverDue = new int[chartMap.size()];
        int i = 0;
        for(String assignment : assignments){
            ChartUtil chartUtil = chartMap.get(assignment);
            Completed[i] = chartUtil.getCompleted();
            NotYetCompleted[i] = chartUtil.getNotYetCompleted();
            OverDue[i] = chartUtil.getOverDue();
            i++;
        }

        barChartDetailUtil barChartDetailUtil1 = new barChartDetailUtil("Done", "bar", "total", Completed);
        barChartDetailUtil barChartDetailUtil2 = new barChartDetailUtil("Catching Up", "bar", "total", NotYetCompleted);
        barChartDetailUtil barChartDetailUtil3 = new barChartDetailUtil("Not Done", "bar", "total", OverDue);

        barChartUtil.setSegments(List.of("Done", "Catching Up", "Not Done"));
        barChartUtil.setCategories(assignments);
        List<barChartDetailUtil> serialData = new ArrayList<>();
        serialData.add(barChartDetailUtil1);
        serialData.add(barChartDetailUtil2);
        serialData.add(barChartDetailUtil3);
        barChartUtil.setSeriesData(serialData);
        return barChartUtil;
    }

    @Override
    public ChartUtil pieData(Long subjectId, Long studentId) {
        List<PersonalChartDTO> completedList = scoreDao.getPersonChartData(subjectId, studentId);

        int completed = 0;
        int notButCan = 0;
        int cannot = 0;
        for (PersonalChartDTO i : completedList) {
            if (i.getCompleteState() == 0) {
                completed++;
            } else if (i.getCompleteState() == 1) {
                notButCan++;
            } else {
                cannot++;
            }
        }
        ChartUtil chartUtil = new ChartUtil();
        chartUtil.setCompleted(completed);
        chartUtil.setNotYetCompleted(notButCan);
        chartUtil.setOverDue(cannot);
        return chartUtil;
    }
}
