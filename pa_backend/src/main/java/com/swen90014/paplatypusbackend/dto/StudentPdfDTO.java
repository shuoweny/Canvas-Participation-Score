package com.swen90014.paplatypusbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentPdfDTO {
    private String type;
    private Double participationScoreWeight;
    private Double participationScoreComplete;
    private String assignmentName;
    private String courseName;
    private String studentName;
    private Double score;
    private String email;
}
