package com.swen90014.paplatypusbackend.controller;

import com.alibaba.excel.EasyExcel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.DocumentException;
import com.swen90014.paplatypusbackend.controller.utils.ResultUtil;
import com.swen90014.paplatypusbackend.domain.Assignment;
import com.swen90014.paplatypusbackend.domain.Course;
import com.swen90014.paplatypusbackend.dto.EasyExcelDTO;
import com.swen90014.paplatypusbackend.dto.ExcelDTO;
import com.swen90014.paplatypusbackend.service.impl.*;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;

import org.springframework.http.HttpHeaders;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/participation")
public class participationExcelController {
    @Autowired
    private ParticipationScoreServiceImpl participationScoreService;
    @Autowired
    private CourseServiceImpl courseService;
    @Autowired
    private AssignmentServiceImpl assignmentService;
    @Autowired
    private StudentServiceImpl studentService;
    @Autowired ScoreServiceImpl scoreService;

    @PutMapping("/calculateParticipationScoreByComplete/{coursecode}")
    public ResultUtil calculateParticipationScoreByComplete(@PathVariable String coursecode){
        Course course = courseService.getCourseByCourseCode(coursecode).get(0);
        participationScoreService.CalculateAndSaveByComplete(course.getId());
        int state = course!=null?200:500;
        String message = course!=null?"SUCCESS":"FAIL";
        return new ResultUtil(state, null, message);

    }

    @PutMapping("/calculateParticipationScoreByScore/{coursecode}")
    public ResultUtil calculateParticipationScoreByScore(@PathVariable String coursecode){
        Course course = courseService.getCourseByCourseCode(coursecode).get(0);
        participationScoreService.CalculateAndSaveByScore(course.getId());
        int state = course!=null?200:500;
        String message = course!=null?"SUCCESS":"FAIL";
        return new ResultUtil(state, null, message);

    }

    @GetMapping("/getExcelDTO/{coursecode}")
    public ResultUtil getExcelDTO(@PathVariable String coursecode){
        Course course = courseService.getCourseByCourseCode(coursecode).get(0);
        List<ExcelDTO> excelDTOS = participationScoreService.getExcelDTO(course.getId());
        int state = excelDTOS!=null?200:500;
        String message = excelDTOS!=null?"SUCCESS":"FAIL";
        return new ResultUtil(state, excelDTOS, message);

    }

    @GetMapping(value = "/exportCompleteExcel/{coursecode}")
    public void exportWeightParticipationScoreExcel(@PathVariable String coursecode, HttpServletResponse response){
        OutputStream outputStream = null;
        Course course = courseService.getCourseByCourseCode(coursecode).get(0);
        List<EasyExcelDTO> data = scoreService.fetchDataForExcel(course.getId());
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Participation_Score");
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Student ID");
        headerRow.createCell(1).setCellValue("Student Name");
        List<String> assignments = assignmentService.getSortedAssignmentByCourseID(course.getId());
//        List<Long> student_ids = new ArrayList<>();

//        //students
//        for(ExcelDTO excelDTO : lists){
//            Long id = excelDTO.getUserId();
//            if(!student_ids.contains(id)){
//                student_ids.add(id);
//            }
//        }

        //assignments
        for(int i=0; i<assignments.size(); i++){
            headerRow.createCell(i+2).setCellValue(assignments.get(i));
        }

        for(int row=1; row<=data.size();row++){
            Row currentRow = sheet.createRow(row);
            EasyExcelDTO dataRow = data.get(row-1);
            currentRow.createCell(0).setCellValue(dataRow.getStudentId());
            currentRow.createCell(1).setCellValue(dataRow.getStudentName());
            for(int i=0; i<assignments.size(); i++){
                BigDecimal cellData = dataRow.getAssignmentScores().get(assignments.get(i))!=null?dataRow.getAssignmentScores().get(assignments.get(i)): BigDecimal.valueOf(0);
                double cellDataDouble = cellData.doubleValue();
                currentRow.createCell(i+2).setCellValue(cellDataDouble);
            }
        }


        try {
            response.setHeader("Content-disposition", "attachment;filename=" + coursecode+  "_weight_participation_score.xlsx");
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.flushBuffer();
            outputStream = response.getOutputStream();
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                    workbook.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

//    @GetMapping(value = "/exportCompleteExcel/{coursecode}")
//    public void exportCompleteParticipationScoreExcel(@PathVariable String coursecode, HttpServletResponse response) throws IOException {
//        Course course = courseService.getCourseByCourseCode(coursecode).get(0);
//        List<EasyExcelDTO> data = scoreService.fetchDataForExcel(course.getId());
//
//        response.setContentType("application/vnd.ms-excel");
//        response.setCharacterEncoding("utf-8");
//        String fileName = URLEncoder.encode(course.getName() + " Participation Scores", "UTF-8");
//        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
//
//        EasyExcel.write(response.getOutputStream(), EasyExcelDTO.class).sheet("Scores").doWrite(data);
//    }



}


