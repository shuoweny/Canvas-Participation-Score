package com.swen90014.paplatypusbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExcelDTO {
    private BigDecimal participationScore;
    private String studentName;
    private Integer userId;
    private String assignmentName;
}
