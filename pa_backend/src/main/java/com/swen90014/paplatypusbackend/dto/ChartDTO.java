package com.swen90014.paplatypusbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChartDTO {
    private String name;
    private Integer compelete_state;
}
