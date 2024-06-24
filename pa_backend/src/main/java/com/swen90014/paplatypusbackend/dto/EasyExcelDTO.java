package com.swen90014.paplatypusbackend.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.swen90014.paplatypusbackend.dto.helper.MapToExcelConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EasyExcelDTO {
    private Integer studentId;
    private String studentName;
    @ExcelProperty(converter = MapToExcelConverter.class)
    private Map<String, BigDecimal> assignmentScores;
}
