package com.swen90014.paplatypusbackend.controller.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class barChartDetailUtil {
    private String name;
    private String type;
    private String stack;
    private int[] data;
}
