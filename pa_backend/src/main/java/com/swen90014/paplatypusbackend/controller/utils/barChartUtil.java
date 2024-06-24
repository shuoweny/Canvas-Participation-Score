package com.swen90014.paplatypusbackend.controller.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class barChartUtil {
    private List<String> segments;
    private Set<String> categories;
    private List<barChartDetailUtil> seriesData;
}
