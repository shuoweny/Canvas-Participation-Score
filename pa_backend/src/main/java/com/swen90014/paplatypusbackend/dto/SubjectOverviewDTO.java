package com.swen90014.paplatypusbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubjectOverviewDTO {
    private String name;
    private Double score;
}
